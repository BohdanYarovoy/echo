document.addEventListener("DOMContentLoaded", function () {
    const userInfoContainer = document.querySelector(".user_info_container");
    const footer = document.querySelector(".footer"); // Клас футера
    const initialTop = userInfoContainer.getBoundingClientRect().top; // Початкова позиція блоку (відстань від верхньої межі сторінки)
    const containerHeight = userInfoContainer.offsetHeight; // Висота блоку
    const distanceToFooter = 20; // Відстань до футера

    document.addEventListener("scroll", function () {
        const scrollPosition = window.scrollY; // Поточна позиція скролу
        const footerTop = footer.getBoundingClientRect().top + scrollPosition; // Верх футера відносно документа
        const maxTop = footerTop - containerHeight - distanceToFooter; // Максимальне місце, де блок може перебувати перед футером

        if (scrollPosition + initialTop + containerHeight + distanceToFooter >= footerTop) {
            // Блок досягає футера і зупиняється
            userInfoContainer.style.position = "absolute";
            userInfoContainer.style.top = `${maxTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        } else if (scrollPosition <= initialTop) {
            // Повернення блоку до початкової позиції
            userInfoContainer.style.position = "fixed";
            userInfoContainer.style.top = `${initialTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        } else {
            // Блок скролиться разом зі сторінкою
            userInfoContainer.style.position = "absolute";
            userInfoContainer.style.top = `${scrollPosition + initialTop}px`;
            userInfoContainer.style.transform = "translate(calc(-50% - 440px), 0)";
        }
    });
});
