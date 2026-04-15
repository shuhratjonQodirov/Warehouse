/* core/app.js - Main Application Logic for UI routing & themes */
const TRANSLATIONS = {
    uz: {
        landing_title: "Ombor Boshqaruv",
        landing_subtitle: "Dizayn va qulaylik uyg'unlashgan tizim",
        landing_btn: "Tizimga Kirish",
        feat_dashboard: "Dashboard", feat_dashboard_desc: "Real vaqt statistikasi",
        feat_products: "Mahsulotlar", feat_products_desc: "Kirim-chiqim boshqaruvi",
        feat_warehouses: "Omborlar", feat_warehouses_desc: "Ko'p tarmoqli omborlar",
        feat_reports: "Hisobotlar", feat_reports_desc: "Barcha jarayon tahlili",
        login_title: "Tizimga Kirish",
        login_subtitle: "Xavfsiz va tezkor kirish",
        login_username: "Username",
        login_password: "Password",
        login_btn: "Kirish",
        login_loading: "Kutilmoqda...",
    }
};

let currentLang = localStorage.getItem('lang') || 'uz';
let currentTheme = localStorage.getItem('theme') || 'light'; // Default to light for clean Apple feel

function initTheme() {
    document.documentElement.setAttribute('data-theme', currentTheme);
}

function toggleTheme() {
    currentTheme = currentTheme === 'light' ? 'dark' : 'light';
    localStorage.setItem('theme', currentTheme);
    document.documentElement.setAttribute('data-theme', currentTheme);
}

function t(key) {
    return (TRANSLATIONS[currentLang] && TRANSLATIONS[currentLang][key]) || key;
}

function translatePage() {
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (el.tagName === 'INPUT' || el.tagName === 'TEXTAREA') {
            el.placeholder = t(key);
        } else {
            el.innerHTML = t(key);
        }
    });
}

function navigateTo(pageId) {
    // Hide all pages
    document.querySelectorAll('.app-page').forEach(page => {
        page.classList.add('hidden');
    });
    // Show requested page
    const target = document.getElementById(pageId);
    if(target) {
        target.classList.remove('hidden');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    translatePage();
    
    // Check if logged in, if so, we'd go to dashboard. But for now, just show landing.
    const token = localStorage.getItem('token');
    if (token) {
        // navigateTo('dashboardPage'); // To be implemented in next step
        navigateTo('landingPage');
    } else {
        navigateTo('landingPage');
    }
});
