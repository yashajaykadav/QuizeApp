function switchTab(tabId) {
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
    
    event.target.classList.add('active');
    document.getElementById(tabId).classList.add('active');

    if (tabId === 'quizzes') {
        loadTodayQuizzes();
    } else if (tabId === 'results') {
        loadResults();
    }
}

async function loadTodayQuizzes() {
    const container = document.getElementById('quizzesContainer');
    try {
        const quizzes = await fetchApi('/student/quizzes/today');
        if (quizzes.length === 0) {
            container.innerHTML = `<p style="color: var(--text-muted)">No quizzes scheduled for today.</p>`;
            return;
        }

        container.innerHTML = quizzes.map(q => `
            <div class="glass-card quiz-card">
                <span class="badge">${q.duration} Mins</span>
                <h3 style="font-size: 1.3rem; margin-top:10px;">${q.topic?.subject?.name || 'Subject'} - ${q.topic?.name || 'Topic'}</h3>
                <p style="color: var(--text-muted); font-size: 0.9rem;">From ${q.startTime} to ${q.endTime}</p>
                <div style="margin-top: auto; padding-top: 20px;">
                    <button class="btn-primary" style="width: 100%" onclick="window.location.href='quiz.html?id=${q.id}'">Start Assessment</button>
                </div>
            </div>
        `).join('');

    } catch (e) {
        container.innerHTML = `<p style="color: var(--danger)">Failed to load scheduled quizzes.</p>`;
    }
}

async function loadResults() {
    const tbody = document.getElementById('resultsTableBody');
    try {
        const data = await fetchApi('/student/results');
        
        // Update summary
        document.getElementById('totA').innerText = data.totalAttempted;
        document.getElementById('avgS').innerText = data.averageScore + '%';
        document.getElementById('bstS').innerText = data.bestScore;

        if (data.attempts.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: var(--text-muted)">You haven't attempted any quizzes yet.</td></tr>`;
            return;
        }

        tbody.innerHTML = data.attempts.map(r => `
            <tr>
                <td style="font-weight: 500">${r.subjectName}</td>
                <td>${r.topicName}</td>
                <td>${r.quizDate}</td>
                <td><span style="color: var(--accent); font-weight: 600;">${r.marksObtained}</span> / ${r.totalMarks}</td>
                <td>${r.percentage}%</td>
                <td>
                    <span class="badge" style="${r.status !== 'COMPLETED' ? 'background:rgba(239, 68, 68, 0.2); color: #f87171;' : 'background:rgba(34, 197, 94, 0.2); color: #4ade80;' }">${r.status}</span>
                </td>
            </tr>
        `).join('');

    } catch(e) {
        tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: var(--danger)">Failed to load results metadata.</td></tr>`;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadTodayQuizzes();
});
