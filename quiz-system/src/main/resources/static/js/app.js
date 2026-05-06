/* ============================================================
   app.js – The Bridge (Frontend + Backend Integration)
   Group A8 - Amrita Online Quiz Management System
   ============================================================ */

const API_BASE_URL = 'http://localhost:8080/api';

/* ─────────────────────────────────────────
   NAVIGATION & SESSION UTILS
   ───────────────────────────────────────── */

function goTo(page) {
    window.location.href = page;
}

function setTeacherSession(name, id) {
    sessionStorage.setItem('teacherLoggedIn', 'true');
    sessionStorage.setItem('teacherName', name);
    sessionStorage.setItem('teacherId', id);
}

function setStudentSession(name, id) {
    sessionStorage.setItem('studentLoggedIn', 'true');
    sessionStorage.setItem('studentName', name);
    sessionStorage.setItem('studentId', id);
}

function logout() {
    sessionStorage.clear();
    goTo('QuizLogin.html');
}

/* ─────────────────────────────────────────
   AUTHENTICATION (Connects to AuthController)
   ───────────────────────────────────────── */

async function login(role) {
    const id = document.getElementById('id-input').value.trim();
    const password = document.getElementById('password-input').value.trim();
    const err = document.getElementById('login-error');

    const endpoint = role === 'teacher' ? '/auth/login/teacher' : '/auth/login/student';

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id, password })
        });

        if (response.ok) {
            const userData = await response.json(); // Returns Student/Teacher object
            if (role === 'teacher') {
                setTeacherSession(userData.name, userData.tchrID);
                goTo('Teacher.html');
            } else {
                setStudentSession(userData.name, userData.rollNo);
                goTo('Student.html');
            }
        } else {
            err.style.display = 'block';
            err.textContent = "Invalid Credentials. Please try again.";
        }
    } catch (e) {
        console.error("Connection Error:", e);
        alert("Backend server is not running!");
    }
}

/* ─────────────────────────────────────────
   TEACHER: QUIZ CREATION (Connects to QuizController)
   ───────────────────────────────────────── */

async function publishQuiz(event) {
    event.preventDefault();

    const title = document.getElementById('quiz-title').value;
    const cards = document.querySelectorAll('.quiz-card');
    const questions = [];

    cards.forEach((card, index) => {
        const n = card.dataset.qnum;
        const text = card.querySelector(`input[name="q${n}"]`).value;
        const options = [
            card.querySelector(`input[name="q${n}_o1"]`).value,
            card.querySelector(`input[name="q${n}_o2"]`).value,
            card.querySelector(`input[name="q${n}_o3"]`).value,
            card.querySelector(`input[name="q${n}_o4"]`).value
        ];
        const ansChar = card.querySelector(`input[name="q${n}_ans"]`).value.toUpperCase();
        const correctIndex = ['A', 'B', 'C', 'D'].indexOf(ansChar);

        questions.push({
            questionText: text,
            options: options,
            correctOption: correctIndex // Matches Integer in Java
        });
    });

    const quizPayload = { title, questions }; // Matches Quiz Entity

    const response = await fetch(`${API_BASE_URL}/quizzes/create`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(quizPayload)
    });

    if (response.ok) {
        alert("Quiz Successfully Published to Database!");
        goTo('QuizLogin.html');
    }
}

/* ─────────────────────────────────────────
   STUDENT: ATTEMPT & EVALUATE
   ───────────────────────────────────────── */

// Fetch all quizzes when the student page loads
async function loadAvailableQuizzes() {
    const response = await fetch(`${API_BASE_URL}/quizzes/all`);
    const quizzes = await response.json();
    const container = document.getElementById('quiz-list-container');

    container.innerHTML = quizzes.map(q => `
        <div class="quiz-card">
            <h3>${q.title}</h3>
            <button onclick="startQuiz(${q.id})">Start Quiz</button>
        </div>
    `).join('');
}

async function submitStudentQuiz(quizId) {
    const quizData = JSON.parse(sessionStorage.getItem('activeQuiz'));
    const selectedAnswers = [];

    quizData.questions.forEach((q, idx) => {
        const selected = document.querySelector(`input[name="q${idx}"]:checked`);
        selectedAnswers.push(selected ? parseInt(selected.value) : -1);
    });

    const studentName = sessionStorage.getItem('studentName');

    // Hits the 'evaluateQuiz' logic in your Service Layer
    const response = await fetch(`${API_BASE_URL}/quizzes/${quizId}/submit?studentName=${studentName}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(selectedAnswers)
    });

    if (response.ok) {
        const result = await response.json(); // The Result object (Composition)
        showResultPanel(result.score, result.questions.length);
    }
}

function showResultPanel(score, total) {
    document.getElementById('quiz-form').style.display = 'none';
    const panel = document.getElementById('result-panel');
    panel.style.display = 'block';
    document.getElementById('result-detail').textContent = `Final Score: ${score} / ${total}`;
}