window.addEventListener("DOMContentLoaded", function () {

    /* Проверка телефона */
    activateMasks();
    function activateMasks() {
        let elems = document.querySelectorAll('input.mask');
        elems.forEach(elem => {
            let mask = new Mask();
            elem.addEventListener("input", mask.create, false);
            elem.addEventListener("focus", mask.create, false);
            elem.addEventListener("click", mask.create, false);
            elem.addEventListener("keydown", mask.create, false);
        })
    }
    function Mask() {
        this.completed = false;
        this.create = (event) => {
            let element = event.target;
            let oldValue = element.value;
            let caretPosition = element.selectionStart;
            let isCaretInEndPosition = oldValue.substring(caretPosition).search(/\d/) === -1;
            let caretMinPosition = 4; // минимально допустимая позиция каретки (не заходить на '+7 (' )

            if (event.type === "keydown") {
                let key = event.key;
                if (key === "Backspace" && oldValue.substring(caretPosition - 1, caretPosition).search(/[\s)-]/) !== -1) {
                    let shift = 1; // на сколько сдвинуть каретку
                    if (oldValue.substring(caretPosition - 2, caretPosition) === ') ') shift = 2;
                    element.setSelectionRange(caretPosition - shift, caretPosition - shift);
                }
                if (key === "Delete" && oldValue.substring(caretPosition, caretPosition + 1).search(/[\s)-]/) !== -1) {
                    let shift = 1;
                    if (oldValue.substring(caretPosition, caretPosition + 2) === ') ') shift = 2;
                    element.setSelectionRange(caretPosition + shift, caretPosition + shift);
                }
                if (key === "ArrowLeft" && caretPosition === caretMinPosition) event.preventDefault();
                if (key === "ArrowRight" && isCaretInEndPosition) event.preventDefault();
                if (key === "ArrowUp") key = "Home";
                if (key === "ArrowDown") key = "End";
                if (key === "Home") {
                    element.setSelectionRange(caretMinPosition, caretMinPosition);
                    event.preventDefault();
                }
                if (key === "End") {
                    let caretMaxPosition = oldValue.indexOf("_");
                    if (caretMaxPosition !== -1) {
                        element.setSelectionRange(caretMaxPosition, caretMaxPosition);
                        event.preventDefault();
                    }
                }
                return;
            }
            // вычисляем значение value элемента
            let newValue = oldValue; // при событии focus & click значение value не меняется
            if (event.type === "input") {
                let matrix = "+7 (___) ___-__-__",
                    i = 0,
                    def = matrix.replace(/\D/g, ""),
                    val = oldValue.replace(/\D/g, "")
                newValue = matrix.replace(/[_\d]/g, function (match) {
                    return i < val.length ? val.charAt(i++) || def.charAt(i) : match
                });
                element.value = newValue;
            }
            // определяем положение каретки
            let caretMaxPosition = newValue.indexOf("_");
            let isCompleted = false;// все ли цифры заполнены
            if (caretMaxPosition === -1) {
                caretMaxPosition = newValue.length;
                isCompleted = true;
            }
            if (isCaretInEndPosition) {
                caretPosition = caretMaxPosition;
            } else if (caretPosition < caretMinPosition) {
                caretPosition = caretMinPosition;
            } else if (caretPosition > caretMaxPosition) {
                caretPosition = caretMaxPosition;
            }
            element.setSelectionRange(caretPosition, caretPosition);
            // если изменилось значение completed
            if (isCompleted !== this.completed) {
                this.completed = !this.completed;
                element.classList.toggle('completed');
            }
        }
    }
    const tels = document.querySelectorAll('input[type=tel]');
    tels.forEach(tel => {
        tel.addEventListener('blur', e => {
            const element = e.target;
            if (element.value === "+7 (___) ___-__-__") {
                element.classList.remove('completed');
                element.classList.remove('uncompleted');
                element.nextElementSibling.style.visibility = "hidden";
                return;
            }
            if (!element.classList.contains('completed')) {
                element.classList.add('uncompleted');
                element.nextElementSibling.style.visibility = "visible";
            } else {
                element.classList.remove('uncompleted');
                element.nextElementSibling.style.visibility = "hidden";
            }
        })
    });

    /* Спойлер */
    const select = document.querySelector('.select');
    const selectValue = document.querySelector('.select_value');
    const selectDropdown = document.querySelector('.select_dropdown');
    const sex = document.querySelector('#sex')
    selectDropdown.addEventListener('click', e => {
        const option = e.target.closest('.select_option');
        if (option) {
            selectValue.textContent = option.textContent;
            e.target.closest('.select').blur()
            select.dispatchEvent(new CustomEvent('change', { detail: option.textContent }))
        }
    })
    select.addEventListener('change', e => sex.value = e.detail)

    /* Проверка электронной почты */
    const emails = document.querySelectorAll('input[type=email]');
    function checkEmail(emailAddress) {
        const sQtext = '[^\\x0d\\x22\\x5c\\x80-\\xff]';
        const sDtext = '[^\\x0d\\x5b-\\x5d\\x80-\\xff]';
        const sAtom = '[^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+';
        const sQuotedPair = '\\x5c[\\x00-\\x7f]';
        const sDomainLiteral = '\\x5b(' + sDtext + '|' + sQuotedPair + ')*\\x5d';
        const sQuotedString = '\\x22(' + sQtext + '|' + sQuotedPair + ')*\\x22';
        const sDomain_ref = sAtom;
        const sSubDomain = '(' + sDomain_ref + '|' + sDomainLiteral + ')';
        const sWord = '(' + sAtom + '|' + sQuotedString + ')';
        const sDomain = sSubDomain + '(\\x2e' + sSubDomain + ')*';
        const sLocalPart = sWord + '(\\x2e' + sWord + ')*';
        const sAddrSpec = sLocalPart + '\\x40' + sDomain;
        const sValidEmail = '^' + sAddrSpec + '$';
        const reValidEmail = new RegExp(sValidEmail);

        return reValidEmail.test(emailAddress);
    }
    emails.forEach(email => {
        email.addEventListener('blur', e => {
            const element = e.target;
            if (element.value === "") {
                element.classList.remove('completed');
                element.classList.remove('uncompleted');
                element.nextElementSibling.style.visibility = "hidden";
                return;
            }
            if (checkEmail(element.value) === false) {
                element.classList.remove('completed');
                element.classList.add('uncompleted');
                element.nextElementSibling.style.visibility = "visible";
            } else {
                element.classList.remove('uncompleted');
                element.classList.add('completed');
                element.nextElementSibling.style.visibility = "hidden";
            }
        })
    });

    /* Проверка паролей */
    const pwd = document.querySelector('.pwd');
    const pwd_r = document.querySelector('.pwd_repeat');
    function checkEqual() {
        if (pwd.value === "" || pwd_r.value === "") {
            return;
        }
        if (pwd.value !== pwd_r.value) {
            pwd_r.classList.remove('completed');
            pwd_r.classList.add('uncompleted');
            pwd_r.nextElementSibling.style.visibility = "visible";
        } else {
            pwd_r.classList.remove('uncompleted');
            pwd_r.classList.add('completed');
            pwd_r.nextElementSibling.style.visibility = "hidden";
        }
    }
    pwd_r.addEventListener('blur', e => {
        if (pwd_r.value.length === 0) {
            pwd_r.classList.remove('uncompleted');
            pwd_r.classList.remove('completed');
            pwd_r.nextElementSibling.style.visibility = "hidden";
            return;
        }
        checkEqual();
    });
    pwd.addEventListener('blur', e => {
        if (pwd.value.length === 0) {
            pwd.classList.remove('completed');
            pwd.classList.remove('uncompleted');
            pwd.nextElementSibling.style.visibility = "hidden";
            return;
        }
        if (pwd.value.length < 6) {
            pwd.classList.remove('completed');
            pwd.classList.add('uncompleted');
            pwd.nextElementSibling.style.visibility = "visible";
        } else {
            pwd.classList.remove('uncompleted');
            pwd.classList.add('completed');
            pwd.nextElementSibling.style.visibility = "hidden";
        }
        checkEqual();
    });
});

function validateForm() {
    if (document.querySelector('#surname').value === "" ||
        document.querySelector('#name').value === "" ||
        document.querySelector('.pwd').value === "" ||
        document.querySelector('.pwd_repeat').value === "" ||
        document.querySelector('input[type=email]').value === "" ||
        document.querySelector('input[type=tel]').value === "+7 (___) ___-__-__" ||
        document.querySelector('#city').value === "" ||
        document.querySelectorAll('.uncompleted').length !== 0) {
        alert("Не все поля формы корректно заполнены. Заполните все поля в соответствии с форматом.");
        return false;
    } else {
        return true;
    }
}