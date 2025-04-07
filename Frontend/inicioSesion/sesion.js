$(document).ready(function () {
    // Alternar entre login y registro
    $('.message a').click(function () {
        $('.form form').animate({
            height: "toggle",
            opacity: "toggle"
        }, "slow");
    });

    // Manejo de inicio de sesión
    $('.login-form').submit(async function (e) {
        e.preventDefault();

        const correo = $('#loginCorreo').val(); // Usamos el ID 'loginCorreo'
        const password = $('#loginPassword').val(); // Usamos el ID 'loginPassword'

        try {
            const response = await fetch("http://localhost:8080/usuarios/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    correo: correo,
                    password: password
                })
            });

            // Imprimir la respuesta completa para ver si hay más detalles en el error
            console.log(response);
            
            if (!response.ok) {
                const errorData = await response.json();
                console.log(errorData); // Esto te ayudará a entender qué está devolviendo el backend
                throw new Error(errorData.message || "Credenciales incorrectas");
            }

            const data = await response.json();
            localStorage.setItem("token", data.token);
            window.location.href = "../Calendario/Calendario.html";

        } catch (error) {
            alert("Error de inicio de sesión: " + error.message);
        }
    });

    // Manejo de registro de usuario
    $('#registroForm').submit(async function (e) {
        e.preventDefault(); // Prevenir el comportamiento por defecto del formulario

        const formData = {
            nombre: $('#nombre').val(),
            apellidos: $('#apellidos').val(),
            correo: $('#correo').val(),
            password: $('#password').val(),
            edad: $('#edad').val(),
            fechaNacimiento: $('#fechaNacimiento').val(),
            sexo: $('#sexo').val(),
            rol: "USER",  // Suponiendo que el rol es siempre USER por defecto
            estado: true   // Suponiendo que el estado está activado por defecto
        };

        try {
            const response = await fetch("http://localhost:8080/usuarios/registro", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.log(errorData); // Esto te ayudará a entender qué está devolviendo el backend
                throw new Error(errorData.message || "Error al registrar el usuario.");
            }

            alert("Usuario registrado exitosamente");
            // Puedes redirigir o limpiar el formulario después del registro

        } catch (error) {
            alert("Error al registrar el usuario: " + error.message);
        }
    });
});
