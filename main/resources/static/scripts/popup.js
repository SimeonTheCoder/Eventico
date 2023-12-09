window.addEventListener('load', function () {
    document.getElementById("close-btn").onclick = function(e) { return myHandler(e); };

    [...document.getElementsByClassName("event-link")].forEach(element => {
        element.onclick = function() { return openEmbedInfo(element.id); };
    });
    [...document.getElementsByClassName("report-link")].forEach(element => {
        element.onclick = function() { return openEmbedReport(element.id); };
    });

    [...document.getElementsByClassName("report-btn")].forEach(element => {
        element.onclick = function() { return openReport(element.id.split("")[0]); };
    });
});

function myHandler(event) {
    document.getElementById("page-display").style.visibility = "hidden";
    document.getElementById("page-display2").style.visibility = "hidden";
    event.preventDefault();
}

async function openEmbedInfo(id) {
    document.getElementById("frame").src = "/event-info-min/" + id;
    await sleep(100);
    document.getElementById("page-display").style.visibility = "visible";
}

async function openEmbedReport(id) {
    document.getElementById("frame").src = "/event-report-min/" + id;
    await sleep(100);
    document.getElementById("page-display").style.visibility = "visible";
}

async function openReport(id) {
    document.getElementById("frame-report").src = "/report/" + id;
    await sleep(100);
    document.getElementById("page-display2").style.visibility = "visible";
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

window.onmessage = async function(e) {
    if (e.data == 'done') {
        document.getElementById("page-display2").style.visibility = "hidden";
    }
};