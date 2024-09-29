document.addEventListener('DOMContentLoaded', () => {
    const description = document.querySelector('.description');

    // Отримуємо збережений опис з Local Storage, якщо він існує
    const savedDescription = localStorage.getItem('userDescription');
    if (savedDescription) {
        description.textContent = savedDescription; // Встановлюємо збережений опис
    }

    description.classList.remove('highlight'); 
});

let isEditing = false;
let originalDescription = ''; 

function toggleEdit() {
    const description = document.querySelector('.description');
    const saveButton = document.querySelector('.save-button');
    const editButton = document.querySelector('.edit-button');

    if (!isEditing) {
        originalDescription = description.textContent;
        description.contentEditable = "true";
        editButton.textContent = "Cancel";
        saveButton.style.display = 'inline-block';
        description.classList.add('highlight');
    } else {
        description.contentEditable = "false";
        editButton.textContent = "Edit";
        saveButton.style.display = 'none';
        description.classList.remove('highlight');

        // Якщо редагування скасоване, повертаємо початковий опис
        if (description.textContent !== originalDescription) {
            description.textContent = originalDescription; 
        }
    }

    isEditing = !isEditing;
}

function saveChanges() {
    const description = document.querySelector('.description');
    const newDescription = description.textContent;

    console.log("Новий опис: " + newDescription);

    // Зберігаємо новий опис у Local Storage
    localStorage.setItem('userDescription', newDescription);
    
    originalDescription = newDescription; 
    toggleEdit(); 
}







const heartIcon = document.querySelector(".like-button .heart-icon");
const likesAmountLabel = document.querySelector(".like-button .likes-amount");

let likesAmount = 7;

heartIcon.addEventListener("click", () => {
  heartIcon.classList.toggle("liked");
  if (heartIcon.classList.contains("liked")) {
    likesAmount++;
  } else {
    likesAmount--;
  }

  likesAmountLabel.innerHTML = likesAmount;
});