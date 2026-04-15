/* dashboard.js - Updated Dashboard with real views, table rendering, and modals */

function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    if(sidebar) sidebar.classList.toggle('collapsed');
}

function getAuthHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
    };
}

function formatMoney(num) {
    if (!num) return '0';
    return Number(num).toLocaleString('uz-UZ') + " so'm";
}

async function loadDashboardView(viewName) {
    const navItems = document.querySelectorAll('.nav-menu .nav-item');
    navItems.forEach(item => item.classList.remove('active'));
    
    // Quick active hack: just based on mapping. Since we click from sidebar via onclick, evt is handy.
    if(window.event && window.event.currentTarget) {
        window.event.currentTarget.classList.add('active');
    }
    
    const titleEl = document.getElementById('pageTitle');
    const outEl = document.getElementById('routerOutlet');
    
    outEl.innerHTML = `<div style="text-align:center; padding: 40px; color: var(--macos-text-secondary);"><div class="spinner"></div> Yuklanmoqda...</div>`;
    
    try {
        switch(viewName) {
            case 'home':
                titleEl.textContent = 'Dashboard';
                outEl.innerHTML = `
                    <div class="card" style="margin-top:20px; text-align:center; padding: 60px 20px;">
                        <h2 style="font-size: 28px; margin-bottom: 12px; font-weight: 500;">Tizimga xush kelibsiz!</h2>
                        <p style="color: var(--macos-text-secondary);">Umumiy holat va so'nggi jarayonlar shu yerda aks etadi.</p>
                    </div>`;
                break;
            case 'products':
                titleEl.textContent = 'Mahsulotlar';
                await fetchAndRenderProducts(outEl);
                break;
            case 'warehouse':
                titleEl.textContent = 'Omborlar';
                await fetchAndRenderWarehouses(outEl);
                break;
            case 'distribution':
                titleEl.textContent = 'Taqsimlash';
                outEl.innerHTML = `<div class="card"><p style="color:var(--macos-text-secondary)">Taqsimot qismi tez orada ulanadi...</p></div>`;
                break;
            case 'reports':
                titleEl.textContent = 'Hisobotlar';
                outEl.innerHTML = `<div class="card"><p style="color:var(--macos-text-secondary)">Hisobot va tarix qismi ulanmoqda...</p></div>`;
                break;
        }
    } catch(err) {
        console.error(err);
        outEl.innerHTML = `<div class="card"><p style="color:var(--macos-danger)">Xatolik yuz berdi: ${err.message}</p></div>`;
    }
}

async function fetchAndRenderProducts(container) {
    const res = await fetch('/api/v1/product/get-all?page=0&size=50', { headers: getAuthHeaders() });
    if(res.status === 401) return logout();
    const result = await res.json();
    const list = result.data || [];
    
    let html = `
    <div class="card" style="padding:0; overflow:hidden;">
        <div style="padding: 24px; display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid var(--macos-border);">
            <h3 style="font-size:18px;">Barcha Mahsulotlar</h3>
            <button class="apple-btn" onclick="openProductModal()">+ Yangi Qo'shish</button>
        </div>
        <div class="apple-table-wrap" style="border:none; border-radius:0;">
            <table class="apple-table">
                <thead>
                    <tr><th>#</th><th>Nomi</th><th>Kategoriya</th><th>O'lchov</th><th>Joriy Narx</th><th>Kritik Limit</th></tr>
                </thead>
                <tbody>
    `;
    
    if(list.length === 0) {
        html += `<tr><td colspan="6" style="text-align:center; padding:30px; color:var(--macos-text-secondary)">Ma'lumot topilmadi</td></tr>`;
    } else {
        list.forEach((p, idx) => {
            html += `<tr style="cursor:pointer;" onclick="editProduct('${p.id}')">
                <td style="color:var(--macos-text-secondary)">${idx + 1}</td>
                <td style="font-weight:500;">${p.name || '—'}</td>
                <td><span class="badge" style="background:rgba(0,113,227,0.1); color:var(--macos-accent)">${p.categoryName || '—'}</span></td>
                <td>${p.unit || '—'}</td>
                <td style="font-weight:600;">${formatMoney(p.currentPrice)}</td>
                <td><span class="${Number(p.criticalLimit)>0 ? 'badge-danger badge' : ''}">${p.criticalLimit || 0} ${p.unit}</span></td>
            </tr>`;
        });
    }
    
    html += `</tbody></table></div></div>`;
    container.innerHTML = html;
}

async function fetchAndRenderWarehouses(container) {
    const res = await fetch('/api/v1/warehouse/get-all?page=0&size=50', { headers: getAuthHeaders() });
    if(res.status === 401) return logout();
    const result = await res.json();
    const list = result.data || [];
    
    let html = `
    <div class="card" style="padding:0; overflow:hidden;">
        <div style="padding: 24px; display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid var(--macos-border);">
            <h3 style="font-size:18px;">Omborlar Ro'yxati</h3>
            <button class="apple-btn" onclick="openWarehouseModal()">+ Ombor yaratish</button>
        </div>
        <div class="apple-table-wrap" style="border:none; border-radius:0;">
            <table class="apple-table">
                <thead>
                    <tr><th>#</th><th>Ombor nomi</th><th>Manzil</th><th>Tavsif</th><th>Status</th></tr>
                </thead>
                <tbody>
    `;
    
    if(list.length === 0) {
        html += `<tr><td colspan="5" style="text-align:center; padding:30px; color:var(--macos-text-secondary)">Ma'lumot topilmadi</td></tr>`;
    } else {
        list.forEach((w, idx) => {
            html += `<tr style="cursor:pointer;" onclick="editWarehouse('${w.id}')">
                <td style="color:var(--macos-text-secondary)">${idx + 1}</td>
                <td style="font-weight:600;">${w.name || '—'}</td>
                <td style="color:var(--macos-text-secondary)">${w.address || '—'}</td>
                <td style="font-size:13px">${w.description || '—'}</td>
                <td><span class="badge badge-success">Faol</span></td>
            </tr>`;
        });
    }
    
    html += `</tbody></table></div></div>`;
    container.innerHTML = html;
}

/* ---------------- MODAL LOGIC ---------------- */
function openModal(id) {
    document.getElementById(id).classList.add('active');
}
function closeModal(id) {
    document.getElementById(id).classList.remove('active');
}

/* Product Modal */
async function loadCategoriesForSelect() {
    const sel = document.getElementById('productCategory');
    if(sel.options.length > 2) return; // Already loaded roughly
    try {
        const res = await fetch('/api/v1/category/get-all?page=0&size=100', { headers: getAuthHeaders() });
        const json = await res.json();
        const list = json.data || [];
        let html = '<option value="">— Tanlang —</option>';
        list.forEach(c => {
            html += `<option value="${c.id}">${c.name}</option>`;
        });
        sel.innerHTML = html;
    } catch(e) { console.error(e); }
}

function openProductModal() {
    document.getElementById('productForm').reset();
    document.getElementById('productId').value = '';
    document.getElementById('productModalTitle').textContent = 'Yangi Mahsulot';
    loadCategoriesForSelect();
    openModal('productModal');
}

function editProduct(id) {
    // Add logic to auto-fill. For now, open it empty as dummy.
    openProductModal();
    document.getElementById('productModalTitle').textContent = 'Mahsulotni tahrirlash';
    // fetch details & fill...
}

async function saveProduct(event) {
    event.preventDefault();
    const btn = document.getElementById('productSaveBtn');
    btn.disabled = true; btn.textContent = "Kutilmoqda...";
    
    // Construct payload
    const payload = {
        name: document.getElementById('productName').value,
        unit: document.getElementById('productUnit').value,
        categoryId: document.getElementById('productCategory').value,
        currentPrice: document.getElementById('productPrice').value || 0,
        criticalLimit: document.getElementById('productLimit').value || 0,
        description: document.getElementById('productDescription').value
    };
    
    try {
        const res = await fetch('/api/v1/product/create', {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(payload)
        });
        const result = await res.json();
        if(res.ok && result.success) {
            closeModal('productModal');
            loadDashboardView('products'); // Reload table
        } else {
            alert("Xatolik: " + result.message);
        }
    } catch(err) {
        alert("Tarmoq xatosi");
    } finally {
        btn.disabled = false; btn.textContent = "Saqlash";
    }
}

/* Warehouse Modal */
function openWarehouseModal() {
    document.getElementById('warehouseForm').reset();
    document.getElementById('whId').value = '';
    document.getElementById('whTitle').textContent = 'Yangi Ombor';
    openModal('warehouseModal');
}

function editWarehouse(id) {
    openWarehouseModal();
    document.getElementById('whTitle').textContent = 'Omborni Tahrirlash';
}

async function saveWarehouse(event) {
    event.preventDefault();
    const btn = document.getElementById('whSaveBtn');
    btn.disabled = true; btn.textContent = "Kutilmoqda...";
    
    const payload = {
        name: document.getElementById('whName').value,
        address: document.getElementById('whAddress').value,
        lat: document.getElementById('whLat').value,
        lon: document.getElementById('whLon').value,
        description: document.getElementById('whDesc').value
    };
    
    try {
        const res = await fetch('/api/v1/warehouse/create', {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(payload)
        });
        const result = await res.json();
        if(res.ok && result.success) {
            closeModal('warehouseModal');
            loadDashboardView('warehouse'); // Reload table
        } else {
            alert("Xatolik: " + result.message);
        }
    } catch(err) {
        alert("Tarmoq xatosi");
    } finally {
        btn.disabled = false; btn.textContent = "Saqlash";
    }
}

function logout() {
    localStorage.removeItem('token');
    navigateTo('landingPage');
    if(document.getElementById('username')) document.getElementById('username').value = '';
    if(document.getElementById('password')) document.getElementById('password').value = '';
}
