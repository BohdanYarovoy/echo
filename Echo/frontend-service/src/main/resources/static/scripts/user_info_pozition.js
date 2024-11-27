document.addEventListener('scroll', () => {
  const userContainer = document.querySelector('.user_info_container');
  const footer = document.querySelector('.footer');

  const footerRect = footer.getBoundingClientRect(); // Позиція футера
  const containerHeight = userContainer.offsetHeight; // Висота контейнера
  const viewportHeight = window.innerHeight; // Висота вікна браузера
  const offset = 16; // Відстань між футером і контейнером у пікселях

  // Перевіряємо, чи футер видно у вікні браузера
  if (footerRect.top < viewportHeight) {
    const overlap = viewportHeight - footerRect.top + offset; // Розрахунок з урахуванням відступу
    userContainer.style.transform = `translate(-50%, calc(-50% - ${overlap}px))`;
  } else {
    userContainer.style.transform = 'translate(-50%, -50%)'; // Центруємо контейнер
  }
});
