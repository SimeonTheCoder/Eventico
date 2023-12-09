var dark = false;

if (document.cookie.split("=")[1] == "dark") {
    document.body.classList.add("dark");
    dark = true;
}

window.addEventListener('load', function () {
    const btn = document.querySelector(".btn-toggle");

    btn.addEventListener("click", function () {
        dark = !dark;
        console.log(dark);

        if (dark) {
            document.body.classList.add("dark");
        } else {
            document.body.classList.remove("dark");
        }

        if(dark) {
            document.cookie = "mode=dark";
        } else {
            document.cookie = "mode=light";
        }
    });
});