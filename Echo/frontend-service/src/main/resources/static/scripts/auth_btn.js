const headerLogin = document.querySelector('.header_login');
const closeBtnLogin = document.querySelector('.close_btn_login');
const loginContainer = document.querySelector('.login_container');
const loginFormBtn = document.querySelector('.login_form_btn')
const loginInput = document.querySelector('.loginInput')
const loginError = document.querySelector('.loginError')

const headerRegister = document.querySelector('.header_register')
const registerContainer = document.querySelector('.register_container')
const closeBtnRegister = document.querySelector('.close_btn_register')
const registerFormBtn = document.querySelector('.register_form_btn')
const registerError = document.querySelector('.registerError')

const blackBackground = document.querySelector('.black_background');

let defaultSetting = true;
let defaultSetting2 = true

// loginb

headerLogin.addEventListener('click', (event) => {
    event.preventDefault(); 
    defaultSetting = !defaultSetting; 
    hideHandler(); 
});

function hideHandler() {
    if (defaultSetting === false) {
        blackBackground.classList.remove('hide');
        loginContainer.classList.remove('hide');
    } else {
        blackBackground.classList.add('hide');
        loginContainer.classList.add('hide');
    }
}

closeBtnLogin.addEventListener('click', () => {
    defaultSetting = !defaultSetting;
    hideHandler(); 
});


// errorMessage
loginFormBtn.addEventListener('click', (event) => {
    event.preventDefault();
    
    const inputs = document.querySelectorAll('.loginInput'); 
    let hasError = false;

    inputs.forEach((input) => {
        if (input.value.trim() === '') { 
            loginError.classList.remove('hide'); 
            hasError = true;
        } else {
            loginError.classList.add('hide'); 
        }
    });

    if (!hasError) {
        blackBackground.classList.add('hide');
        loginContainer.classList.add('hide');

       
        inputs.forEach((input) => {
            input.value = '';  
        });
    }
});

// register


headerRegister.addEventListener('click', (event) => {
    event.preventDefault(); 
    defaultSetting2 = !defaultSetting2; 
    hideHandler2(); 
});

function hideHandler2() {
    if (defaultSetting2 === false) {
        blackBackground.classList.remove('hide');
        registerContainer.classList.remove('hide');
    } else {
        blackBackground.classList.add('hide');
        registerContainer.classList.add('hide');
    }
}

closeBtnRegister.addEventListener('click', () => {
    defaultSetting2 = !defaultSetting2;
    hideHandler2(); 
});

// errorMessage
registerFormBtn.addEventListener('click', (event) => {
    event.preventDefault();
    
    const inputs = document.querySelectorAll('.registerInput'); 
    let hasError = false;

    inputs.forEach((input) => {
        if (input.value.trim() === '') { 
            registerError.classList.remove('hide'); 
            hasError = true;
        } else {
            registerError.classList.add('hide'); 
        }
    });

    if (!hasError) {
        blackBackground.classList.add('hide');
        registerContainer.classList.add('hide');

       
        inputs.forEach((input) => {
            input.value = '';  
        });
    }
});



const signUp = document.querySelector('.sign_up')
const logIn = document.querySelector('.log_in')

signUp.addEventListener('click', () => {
        loginContainer.classList.add('hide');
        registerContainer.classList.remove('hide');       
})

logIn.addEventListener('click', () => {
    loginContainer.classList.remove('hide');
    registerContainer.classList.add('hide');       
})




