
const API_BASE_URL = "/api/applications";

// Get application ID from URL
const params = new URLSearchParams(window.location.search);
const applicationId = params.get("id");

const statusSelect = document.getElementById("statusSelect");
const saveButton = document.getElementById("saveStatusBtn");
const statusMessage = document.getElementById("statusMessage");

// Load selected applicant
async function loadApplicant() {

    if (!applicationId) {
        showError("Application ID is missing.");
        return;
    }

    try {
        const response = await fetch(
            `${API_BASE_URL}/${encodeURIComponent(applicationId)}`
        );

        if (!response.ok) {
            throw new Error("Applicant not found.");
        }

        const app = await response.json();

        displayApplicant(app);

    } catch (error) {
        showError(error.message);
    }
}

// Display applicant information
function displayApplicant(app) {

    document.title = `${app.fullName} | ApplyTrack`;

    document.getElementById("fullName").textContent = app.fullName || "-";
    document.getElementById("email").textContent = app.email || "-";
    document.getElementById("applicationId").textContent =
        `Application ID: ${app.applicationId}`;

    document.getElementById("profileAvatar").textContent =
        (app.fullName || "A").charAt(0).toUpperCase();

    document.getElementById("nameValue").textContent = app.fullName || "-";
    document.getElementById("emailValue").textContent = app.email || "-";
    document.getElementById("mobileValue").textContent = app.mobile || "-";
    document.getElementById("dateValue").textContent =
        formatDate(app.submittedAt);

    document.getElementById("collegeValue").textContent = app.college || "-";
    document.getElementById("degreeValue").textContent =
        app.degreeProgram || "-";
    document.getElementById("yearValue").textContent = app.year || "-";
    document.getElementById("domainValue").textContent =
        app.preferredDomain || "-";

    document.getElementById("skillsValue").textContent =
        app.technicalSkills || "Not provided";

    document.getElementById("interestValue").textContent =
        app.statementOfInterest || "Not provided";

    const resumeLink = document.getElementById("resumeLink");

    const resumeURL = (app.resumeLink || "").trim();

    if (/^https?:\/\/\S+$/i.test(resumeURL)) {
        resumeLink.href = resumeURL;
        resumeLink.style.display = "inline";
    } else {
        resumeLink.removeAttribute("href");
        resumeLink.style.display = "none";
    }

    statusSelect.value = app.status || "NEW";
    updateStatusBadge(app.status || "NEW");
}

// Format date safely
function formatDate(value) {

    if (!value) return "Not provided";

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {
        return value;
    }

    return date.toLocaleDateString("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric"
    });
}

// Update visible status badge
function updateStatusBadge(status) {

    const badge = document.getElementById("profileStatus");

    badge.textContent = status;
    badge.className = `status status-${status}`;
}

// Save updated status to backend
async function saveStatus() {

    const status = statusSelect.value;

    saveButton.disabled = true;
    saveButton.textContent = "Saving...";
    statusMessage.textContent = "";

    try {

        const response = await fetch(
            `${API_BASE_URL}/${encodeURIComponent(applicationId)}/status`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ status })
            }
        );

        if (!response.ok) {
            throw new Error("Unable to update application status.");
        }

        const updatedApplication = await response.json();

        updateStatusBadge(updatedApplication.status);

        statusMessage.textContent =
            "✓ Application status updated successfully!";

    } catch (error) {

        statusMessage.style.color = "#b33a3a";
        statusMessage.textContent = error.message;

    } finally {

        saveButton.disabled = false;
        saveButton.textContent = "Save Status";
    }
}

function showError(text) {
    document.getElementById("fullName").textContent = "Unable to load applicant";
    statusMessage.style.color = "#b33a3a";
    statusMessage.textContent = text;
}

// Initial loading
loadApplicant();
