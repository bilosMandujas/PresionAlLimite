// Clase BotonCalendario: Representa un botón de un día en el calendario
class BotonCalendario {
    constructor(dia, esHoy) {
        this.dia = dia;  // Día específico del mes
        this.esHoy = esHoy;  // Booleano para saber si es el día de hoy
    }

    // Función que crea el HTML del botón para un día específico
    crearBoton() {
        return `
            <td class="${this.esHoy ? "hoy" : ""}">  <!-- Si es hoy, agrega la clase "hoy" -->
                <button class="boton-dia" data-dia="${this.dia}">${this.dia}</button> <!-- Asocia el día al botón -->
            </td>
        `;
    }
}

var actual = new Date(); // Obtiene la fecha actual
var mesActual = actual.getMonth() + 1;  // Obtiene el mes actual (1-12)
var añoActual = actual.getFullYear();  // Obtiene el año actual (4 dígitos)

// Función que muestra el calendario de un mes específico
function mostrarCalendario(year, month) {
    mesActual = month;
    añoActual = year;

    var now = new Date(year, month - 1, 1);
    var last = new Date(year, month, 0);
    var primerDiaSemana = now.getDay() === 0 ? 7 : now.getDay();
    var ultimoDiaMes = last.getDate();

    var resultado = "<tr>";
    var dia = 0;
    var last_cell = primerDiaSemana + ultimoDiaMes;

    var hoy = new Date();
    var diaHoy = hoy.getDate();
    var mesHoy = hoy.getMonth() + 1;
    var añoHoy = hoy.getFullYear();

    for (var i = 1; i <= 42; i++) {
        if (i == primerDiaSemana) dia = 1;

        if (i < primerDiaSemana || i >= last_cell) {
            resultado += "<td class='vacía'>&nbsp;</td>";
        } else {
            let boton = new BotonCalendario(dia, dia === diaHoy && month === mesHoy && year === añoHoy);
            resultado += boton.crearBoton();
            dia++;
        }

        if (i % 7 == 0) {
            if (dia > ultimoDiaMes) break;
            resultado += "</tr><tr>";
        }
    }
    resultado += "</tr>";

    // Insertar el calendario en la tabla
    document.getElementById("calendar").getElementsByTagName("tbody")[0].innerHTML = resultado;

    // Actualizar el mes y año en el HTML
    var meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
    document.getElementById("mesActualLabel").innerText = `${meses[mesActual - 1]} ${añoActual}`;
}

// Función para cerrar el formulario
function cerrarFormulario() {
    document.getElementById("formContainer").style.display = "none";  // Ocultar el formulario
}

// Función para ir al día y mes actual
function volverHoy() {
    var actual = new Date();  // Obtener la fecha actual
    mostrarCalendario(actual.getFullYear(), actual.getMonth() + 1);  // Llamar a la función para mostrar el calendario con la fecha actual
    document.getElementById("selectedDay").innerText = actual.getDate();  // Establecer el día seleccionado como hoy
    document.getElementById("day").value = actual.getDate();  // Guardar el día actual en el campo oculto
    document.getElementById("fecha").value = `${String(actual.getDate()).padStart(2, '0')}/${String(actual.getMonth() + 1).padStart(2, '0')}/${actual.getFullYear()}`;  // Mostrar la fecha actual en el formulario
}

// Evento para el botón "Volver al día actual"
document.getElementById("volverHoy").addEventListener("click", function() {
    volverHoy();  // Llamar a la función para volver al día actual
});

// Función para cambiar el mes
function cambiarMes(direccion) {
    mesActual += direccion;

    if (mesActual < 1) {
        mesActual = 12;
        añoActual--;
    } else if (mesActual > 12) {
        mesActual = 1;
        añoActual++;
    }

    mostrarCalendario(añoActual, mesActual);
}

// Evento delegado para escuchar los clics en los botones de los días del calendario
document.getElementById('calendar').addEventListener('click', function (event) {
    if (event.target.classList.contains('boton-dia')) {  // Si el clic es en un botón de día
        const diaSeleccionado = event.target.dataset.dia;  // Obtener el día desde el atributo "data-dia" del botón

        // Formatear la fecha en formato DD/MM/YYYY para mostrar en el formulario
        const fechaSeleccionada = `${String(diaSeleccionado).padStart(2, '0')}/${String(mesActual).padStart(2, '0')}/${añoActual}`;

        // Actualizar el formulario con la fecha seleccionada en formato DD/MM/YYYY
        document.getElementById('selectedDay').innerText = diaSeleccionado;
        document.getElementById('day').value = diaSeleccionado;
        document.getElementById('fecha').value = fechaSeleccionada;  // Mostrar en el formato DD/MM/YYYY

        // También actualizar la fecha en el formato YYYY-MM-DD en el campo de texto de fecha (para backend)
        const fechaFormateada = `${añoActual}-${String(mesActual).padStart(2, '0')}-${String(diaSeleccionado).padStart(2, '0')}`;
        document.getElementById('fecha').setAttribute('data-fecha', fechaFormateada);  // Guardar la fecha formateada para el backend

        // Mostrar el formulario
        document.getElementById('formContainer').style.display = 'block';
    }
});

// Inicializar el calendario con la fecha actual
mostrarCalendario(actual.getFullYear(), actual.getMonth() + 1);

// Evento para enviar el formulario
document.getElementById("formContainer").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita que el formulario se envíe de la manera tradicional

    // Obtén los datos del formulario
    const presionAlta = document.getElementById("presionAlta").value;
    const presionBaja = document.getElementById("presionBaja").value;
    const ritmoCardiaco = document.getElementById("ritmoCardiaco").value;
    const observaciones = document.getElementById("observaciones").value;

    // Obtener la fecha formateada en formato YYYY-MM-DD
    const fecha = document.getElementById("fecha").getAttribute('data-fecha');
    console.log("Fecha capturada:", fecha);  // Verificar que la fecha esté en el formato correcto

    // Crea el objeto con los datos que se enviarán al backend
    const registro = {
        presionAlta: presionAlta,
        presionBaja: presionBaja,
        ritmoCardiaco: ritmoCardiaco,
        observaciones: observaciones,
        fecha: fecha  // Enviar la fecha en formato YYYY-MM-DD
    };

    // Enviar los datos al backend usando fetch
    fetch("http://localhost:8080/api/empleados", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(registro)
    })
    .then(response => response.json())
    .then(data => {
        console.log("Registro guardado:", data);
        alert("¡Registro guardado correctamente!");

        // Limpiar los campos del formulario después de guardar
        document.getElementById("eventForm").reset(); // Restablecer el formulario a su estado inicial
        document.getElementById('formContainer').style.display = 'none'; // Ocultar el formulario nuevamente

    })
    .catch(error => {
        console.error("Error al guardar el registro:", error);
        alert("Hubo un error al guardar el registro.");
    });
});
