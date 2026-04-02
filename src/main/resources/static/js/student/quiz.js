let questions = [];
let currentIndex = 0;
let answersMap = {}; // Maps question.id -> selectedOption
let attemptId = null;
let timerInterval = null;

// Parse Quiz ID from URL parameter (e.g. quiz.html?id=5)
const urlParams = new URLSearchParams(window.location.search);
const quizId = urlParams.get('id');

async function initQuiz() {
    if (!quizId) {
        showToast('Invalid Quiz Access', 'error');
        window.location.href = '/student-dashboard.html';
        return;
    }

    try {
        // 1. Alert Backend: Start Attempt
        const attempt = await fetchApi(`/student/attempts/start/${quizId}`, { method: 'POST' });
        attemptId = attempt.id;

        // 2. Fetch Questions
        questions = await fetchApi(`/student/quizzes/${quizId}/questions`);
        
        if (questions.length === 0) {
            throw new Error("This quiz has no questions");
        }

        // Initialize empty answers
        questions.forEach(q => answersMap[q.id] = null);

        document.getElementById('loadingWrapper').style.display = 'none';
        document.getElementById('quizWrapper').style.display = 'grid';
        document.getElementById('qTotal').innerText = questions.length;

        // Build Right Navigation Nodes
        buildNavigationPanel();

        // Load First Question
        loadQuestion(0);

        // Start Timer (Default 60 mins if unprovided by quiz details, since our /questions API 
        // doesn't return quiz duration, we can assume 60 or load it via another endpoint, but let's hardcode 60m for demo, or read it if we had it)
        startTimer(60 * 60);

    } catch (e) {
        showToast(e.message, 'error');
        setTimeout(() => window.location.href = '/student-dashboard.html', 3000);
    }
}

function buildNavigationPanel() {
    const pnl = document.getElementById('qPanel');
    pnl.innerHTML = '';
    questions.forEach((q, idx) => {
        const btn = document.createElement('button');
        btn.className = 'q-node';
        btn.id = `qnode_${idx}`;
        btn.innerText = idx + 1;
        btn.onclick = () => loadQuestion(idx);
        pnl.appendChild(btn);
    });
}

function loadQuestion(index) {
    if (index < 0 || index >= questions.length) return;
    
    // Update old node status before moving
    updateNodeStatus(currentIndex);
    
    currentIndex = index;
    const q = questions[currentIndex];
    
    // Update new node to "current" (orange color)
    document.querySelectorAll('.q-node').forEach(n => n.classList.remove('current'));
    const curBtn = document.getElementById(`qnode_${currentIndex}`);
    if (curBtn) curBtn.classList.add('current');

    // DOM Population
    document.getElementById('qDisplayNum').innerText = currentIndex + 1;
    document.getElementById('qText').innerText = q.questionText;
    
    document.getElementById('optA').innerText = q.optionA;
    document.getElementById('optB').innerText = q.optionB;
    document.getElementById('optC').innerText = q.optionC;
    document.getElementById('optD').innerText = q.optionD;

    // Reset styles
    document.querySelectorAll('.option-label').forEach(lbl => lbl.classList.remove('selected'));
    
    // Pre-select if already answered
    document.querySelectorAll('input[name="answer"]').forEach(radio => {
        radio.checked = false;
        if (answersMap[q.id] === radio.value) {
            radio.checked = true;
            radio.parentElement.classList.add('selected');
        }
    });

    // Handle next/prev controls
    document.getElementById('btnPrev').disabled = (currentIndex === 0);
    document.getElementById('btnPrev').style.opacity = (currentIndex === 0) ? '0.5' : '1';
    
    const isLast = (currentIndex === questions.length - 1);
    document.getElementById('btnNext').innerText = isLast ? 'Finish Quiz' : 'Next Question';
}

function updateNodeStatus(idx) {
    const qid = questions[idx].id;
    const btn = document.getElementById(`qnode_${idx}`);
    if(!btn) return;
    
    if (answersMap[qid] !== null) {
        btn.classList.add('attempted'); // Green
    } else {
        btn.classList.remove('attempted'); // Keep Red
    }
}

function navigate(direction) {
    if (currentIndex === questions.length - 1 && direction === 1) {
        // Automatically acts as manual submit trigger if clicked on last Next
        submitQuizManually();
        return;
    }
    loadQuestion(currentIndex + direction);
}

function markAnswer(alphaOption) {
    const q = questions[currentIndex];
    answersMap[q.id] = alphaOption;
    
    document.querySelectorAll('.option-label').forEach(lbl => lbl.classList.remove('selected'));
    document.querySelector(`input[value="${alphaOption}"]`).parentElement.classList.add('selected');
    
    updateNodeStatus(currentIndex);
}

function startTimer(durationInSeconds) {
    let t = durationInSeconds;
    const elm = document.getElementById('timerDisplay');
    
    timerInterval = setInterval(() => {
        t--;
        if (t <= 0) {
            clearInterval(timerInterval);
            elm.innerText = "00:00";
            elm.style.color = 'red';
            showToast('Time Expired! Auto-submitting...', 'warning');
            forceSubmit();
        } else {
            let m = Math.floor(t / 60);
            let s = t % 60;
            if (m < 10) m = '0' + m;
            if (s < 10) s = '0' + s;
            elm.innerText = `${m}:${s}`;
            
            if (t < 300) { // < 5 mins
                elm.style.color = 'red';
            }
        }
    }, 1000);
}

async function submitQuizManually() {
    if (confirm("Are you sure you want to completely finish the quiz and submit your answers?")) {
        await forceSubmit();
    }
}

async function forceSubmit() {
    if (timerInterval) clearInterval(timerInterval);
    document.body.innerHTML = '<h2 style="text-align: center; padding: 100px; color: white;">Submitting your results... Please wait.</h2>';
    
    try {
        await fetchApi(`/student/attempts/${attemptId}/submit`, {
            method: 'POST',
            body: JSON.stringify({
                selectedOptions: answersMap
            })
        });
        showToast('Successfully submitted!');
        // Small delay to allow toast to render then jump to dashboard
        setTimeout(() => window.location.href = '/student-dashboard.html', 1500);
    } catch(err) {
        // Prevent fatal lockdown if network fail
        document.body.innerHTML = `
            <div style="text-align:center; margin-top:100px;">
                <h2 style="color:red">Failed to submit! Retaining connection...</h2>
                <button onclick="forceSubmit()" style="padding: 10px 20px; font-size:18px; margin-top:20px;">Retry Submit</button>
            </div>
        `;
    }
}

window.onload = initQuiz;
