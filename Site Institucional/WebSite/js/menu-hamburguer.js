class MobileNavbar {
    constructor(MenuHamburguer, navList, navLinks) {
        this.MenuHamburguer = document.querySelector(MenuHamburguer);
        this.navList = document.querySelector(navList);
        this.navLinks = document.querySelectorAll(navLinks);
        this.activeClass = "active";
    }

    addClickEvent() {
        this.MenuHamburguer.addEventListener("click", () => console.log
        ("Hey"));
    }

    init() {
        if(this.MenuHamburguer) {
            this.addClickEvent();
        }

        return this;
    }
}

const mobileNavBar = new MobileNavbar(
    "menu-hamburguer",
    "nav-list",
    "nav-list li"
);
mobileNavBar.init();