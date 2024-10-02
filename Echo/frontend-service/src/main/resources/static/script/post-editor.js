// Відкриття модального вікна
document.getElementById("open-editor").addEventListener("click", function() {
  document.getElementById("editor-modal").style.display = "flex"; // Показати модальне вікно
  document.body.classList.add("no-scroll"); // Заблокувати прокрутку сторінки
});

// Закриття модального вікна
document.getElementById("close-button").addEventListener("click", function() {
  document.getElementById("editor-modal").style.display = "none"; // Приховати модальне вікно
  document.body.classList.remove("no-scroll"); // Відновити прокрутку сторінки
});
