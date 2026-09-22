const API_URL = "/api/products";

const dom = {
    tableBody: document.getElementById("productsTableBody"),
    emptyState: document.getElementById("emptyState"),
    searchInput: document.getElementById("searchInput"),
    categoryFilter: document.getElementById("categoryFilter"),
    resetBtn: document.getElementById("resetFiltersBtn"),
    openModalBtn: document.getElementById("openNewModalBtn"),
    totalItems: document.getElementById("totalItemsCount"),
    totalStock: document.getElementById("totalStockCount"),

    dialog: document.getElementById("productDialog"),
    form: document.getElementById("productForm"),
    dialogTitle: document.getElementById("dialogTitle"),
    closeDialogBtn: document.getElementById("closeDialogBtn"),
    cancelDialogBtn: document.getElementById("cancelDialogBtn"),
    formError: document.getElementById("formErrorMessage"),

    fieldId: document.getElementById("formProductId"),
    fieldTitle: document.getElementById("formTitle"),
    fieldFranchise: document.getElementById("formFranchise"),
    fieldCategory: document.getElementById("formCategory"),
    fieldCharacter: document.getElementById("formCharacter"),
    fieldPrice: document.getElementById("formPrice"),
    fieldStock: document.getElementById("formStock"),
    fieldYear: document.getElementById("formYear"),
    fieldRating: document.getElementById("formRating"),
};

// Fetch API helper
async function fetchProducts() {
    const query = dom.searchInput.value.trim();
    const category = dom.categoryFilter.value;

    const params = new URLSearchParams();
    if (query) params.append("title", query);
    if (category) params.append("category", category);

    const url = params.toString() ? `${API_URL}?${params.toString()}` : API_URL;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Помилка завантаження: ${response.statusText}`);
    }
    return response.json();
}

async function fetchOneProduct(id) {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) {
        throw new Error(`Товар #${id} не знайдено`);
    }
    return response.json();
}

async function createProduct(payload) {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    return handleApiResponse(response);
}

async function updateProduct(id, payload) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    return handleApiResponse(response);
}

async function deleteProduct(id) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });
    if (!response.ok) {
        throw new Error("Не вдалося видалити товар");
    }
}

async function handleApiResponse(response) {
    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
        if (data.validationErrors) {
            const msgs = Object.entries(data.validationErrors).map(([f, m]) => `${f}: ${m}`).join(", ");
            throw new Error(msgs);
        }
        throw new Error(data.message || `Помилка сервера (${response.status})`);
    }
    return data;
}

// Escaping
function escapeHtml(str) {
    if (!str) return "";
    return String(str).replace(/[&<>"']/g, m => ({
        "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;"
    }[m]));
}

// Render Products
function renderProducts(products) {
    dom.tableBody.innerHTML = "";
    dom.emptyState.hidden = products.length > 0;

    let totalStockSum = 0;

    products.forEach(item => {
        totalStockSum += (item.stock || 0);

        const tr = document.createElement("tr");

        let stockClass = "stock-badge";
        if (item.stock === 0) stockClass += " out-of-stock";
        else if (item.stock < 10) stockClass += " low-stock";

        tr.innerHTML = `
            <td><strong style="color: var(--text-muted)">#${item.id}</strong></td>
            <td><strong>${escapeHtml(item.title)}</strong></td>
            <td><span style="color: var(--accent-cyan); font-weight: 500;">${escapeHtml(item.franchise)}</span></td>
            <td><span class="cat-badge ${escapeHtml(item.category)}">${escapeHtml(item.category)}</span></td>
            <td>${item.characterName ? escapeHtml(item.characterName) : '<span style="color: var(--text-muted)">—</span>'}</td>
            <td class="text-right"><span class="price-tag">$${Number(item.price).toFixed(2)}</span></td>
            <td class="text-right"><span class="${stockClass}">${item.stock} шт.</span></td>
            <td class="text-center">${item.releaseYear || "—"}</td>
            <td class="text-center"><span class="rating-pill">★ ${Number(item.rating || 0).toFixed(1)}</span></td>
            <td class="text-center">
                <button class="btn btn-sm btn-edit" data-edit="${item.id}">Ред.</button>
                <button class="btn btn-sm btn-danger" data-delete="${item.id}">✕</button>
            </td>
        `;

        dom.tableBody.appendChild(tr);
    });

    dom.totalItems.textContent = products.length;
    dom.totalStock.textContent = totalStockSum;
}

// Reload table
async function reloadCatalog() {
    try {
        const products = await fetchProducts();
        renderProducts(products);
    } catch (err) {
        console.error(err);
        dom.emptyState.hidden = false;
        dom.tableBody.innerHTML = "";
    }
}

// Dialog handling
function openModal(product = null) {
    dom.formError.hidden = true;
    dom.form.reset();

    if (product) {
        dom.dialogTitle.textContent = `Редагувати: ${product.title}`;
        dom.fieldId.value = product.id;
        dom.fieldTitle.value = product.title || "";
        dom.fieldFranchise.value = product.franchise || "";
        dom.fieldCategory.value = product.category || "FIGURE";
        dom.fieldCharacter.value = product.characterName || "";
        dom.fieldPrice.value = product.price || 0;
        dom.fieldStock.value = product.stock || 0;
        dom.fieldYear.value = product.releaseYear || 2024;
        dom.fieldRating.value = product.rating || 5.0;
    } else {
        dom.dialogTitle.textContent = "Додати новий аніме-товар";
        dom.fieldId.value = "";
        dom.fieldCategory.value = "FIGURE";
        dom.fieldYear.value = 2024;
        dom.fieldRating.value = 4.8;
    }

    dom.dialog.showModal();
}

function closeModal() {
    dom.dialog.close();
}

// Event Listeners
dom.openModalBtn.addEventListener("click", () => openModal(null));
dom.closeDialogBtn.addEventListener("click", closeModal);
dom.cancelDialogBtn.addEventListener("click", closeModal);

dom.searchInput.addEventListener("input", () => reloadCatalog());
dom.categoryFilter.addEventListener("change", () => reloadCatalog());

dom.resetBtn.addEventListener("click", () => {
    dom.searchInput.value = "";
    dom.categoryFilter.value = "";
    reloadCatalog();
});

// Table action buttons (Edit / Delete)
dom.tableBody.addEventListener("click", async (e) => {
    const editId = e.target.getAttribute("data-edit");
    const delId = e.target.getAttribute("data-delete");

    if (editId) {
        try {
            const product = await fetchOneProduct(editId);
            openModal(product);
        } catch (err) {
            alert(err.message);
        }
    } else if (delId) {
        if (confirm(`Ви дійсно бажаєте видалити товар #${delId}?`)) {
            try {
                await deleteProduct(delId);
                await reloadCatalog();
            } catch (err) {
                alert(err.message);
            }
        }
    }
});

// Form Submit
dom.form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        title: dom.fieldTitle.value.trim(),
        franchise: dom.fieldFranchise.value.trim(),
        category: dom.fieldCategory.value,
        characterName: dom.fieldCharacter.value.trim() || null,
        price: parseFloat(dom.fieldPrice.value) || 0,
        stock: parseInt(dom.fieldStock.value, 10) || 0,
        releaseYear: parseInt(dom.fieldYear.value, 10) || null,
        rating: parseFloat(dom.fieldRating.value) || null
    };

    const id = dom.fieldId.value;

    try {
        if (id) {
            await updateProduct(id, payload);
        } else {
            await createProduct(payload);
        }
        closeModal();
        await reloadCatalog();
    } catch (err) {
        dom.formError.textContent = err.message;
        dom.formError.hidden = false;
    }
});

// Initial load
reloadCatalog();
