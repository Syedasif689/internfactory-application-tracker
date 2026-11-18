
const API_BASE_URL = "/api/applications";

const tableBody = document.getElementById("applicationTable");
const searchInput = document.getElementById("searchInput");
const domainFilter = document.getElementById("domainFilter");
const message = document.getElementById("message");

let searchTimer;

// Load dashboard statistics
async function loadCounts() {
    try {
        const response = await fetch(`${API_BASE_URL}/counts`);

        if (!response.ok) {
            throw new Error("Unable to load dashboard statistics");
        }

        const counts = await response.json();

        document.getElementById("totalCount").textContent = counts.total;
        document.getElementById("newCount").textContent = counts.new;
        document.getElementById("shortlistedCount").textContent = counts.shortlisted;
        document.getElementById("rejectedCount").textContent = counts.rejected;

    } catch (error) {
        message.textContent = error.message;
    }
}

// Load applications with search and domain filtering
async function loadApplications() {

    const search = searchInput.value.trim();
    const domain = domainFilter.value;

    const params = new URLSearchParams();

    if (search) params.append("search", search);
    if (domain) params.append("domain", domain);

    tableBody.innerHTML = `
        <tr>
            <td colspan="6" class="loading">Loading applications...</td>
        </tr>
    `;

    message.textContent = "";

    try {
        const response = await fetch(
            `${API_BASE_URL}?${params.toString()}`
        );

        if (!response.ok) {
            throw new Error("Unable to load applications");
        }

        const applications = await response.json();

        renderApplications(applications);

    } catch (error) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="empty-state">
                    Failed to load applications. Please refresh.
                </td>
            </tr>
        `;

        message.textContent = error.message;
    }
}

// Display applications in the table
function renderApplications(applications) {

    document.getElementById("recordCount").textContent =
        `${applications.length} Records`;

    if (applications.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="empty-state">
                    No applications found.
                </td>
            </tr>
        `;
        return;
    }

    tableBody.innerHTML = applications.map(app => {

        const status = app.status || "NEW";

        return `
            <tr>
                <td>
                    <div class="applicant-name">
                        ${escapeHTML(app.fullName)}
                    </div>
                    <div class="applicant-email">
                        ${escapeHTML(app.email)}
                    </div>
                </td>

                <td>${escapeHTML(app.college)}</td>

                <td>${escapeHTML(app.year)}</td>

                <td>
                    <div class="domain-text">
                        ${escapeHTML(app.preferredDomain)}
                    </div>
                </td>

                <td>
                    <span class="status status-${status}">
                        ${status}
                    </span>
                </td>

                <td>
                    <button class="view-btn"
                        onclick="viewApplication('${encodeURIComponent(app.applicationId)}')">
                        View →
                    </button>
                </td>
            </tr>
        `;
    }).join("");
}

// Escape user-provided data before inserting it into HTML
function escapeHTML(value) {
    return String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[character]);
}

// Open applicant details page
function viewApplication(id) {
    window.location.href = `details.html?id=${id}`;
}

// Search with a short delay
searchInput.addEventListener("input", () => {
    clearTimeout(searchTimer);

    searchTimer = setTimeout(() => {
        loadApplications();
    }, 300);
});

// Domain filter
domainFilter.addEventListener("change", loadApplications);

// Initial dashboard loading
async function initializeDashboard() {
    await Promise.all([
        loadCounts(),
        loadApplications()
    ]);
}

initializeDashboard();
