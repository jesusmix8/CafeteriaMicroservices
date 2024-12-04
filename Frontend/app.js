const API_URL = "http://localhost:8080/api/v1/productos";

let productoEnEdicion = null; // Variable para manejar la edición de productos

// Obtener productos y llenar la tabla
async function cargarProductos() {
  try {
    const response = await fetch(API_URL);

    if (!response.ok) {
      console.error("Error al cargar productos:", response.statusText);
      return;
    }

    const productos = await response.json();
    const tabla = document.getElementById("productosTabla");
    tabla.innerHTML = "";

    productos.forEach((producto) => {
      const row = `
        <tr>
          <td>${producto.id}</td>
          <td>${producto.nombre}</td>
          <td>${producto.descripcion}</td>
          <td>${producto.precio}</td>
          <td>${producto.cantidad}</td>
          <td>${producto.categoria}</td>
          <td>${producto.fecha}</td>
          <td>
            <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${producto.id})">Eliminar</button>
            <button class="btn btn-warning btn-sm" onclick="mostrarFormularioEditar(${producto.id})">Editar</button>
          </td>
        </tr>
      `;
      tabla.innerHTML += row;
    });
  } catch (error) {
    console.error("Error al conectar con la API:", error);
  }
}

// Evento del formulario (crear o editar)
document
  .getElementById("productoForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();

    if (!validarTexto(document.getElementById("nombre").value)) {
      mostrarError("El campo 'nombre' debe contener solo texto.");
      return;
    }

    if (!validarTexto(document.getElementById("categoria").value)) {
      mostrarError("El campo 'categoría' debe contener solo texto.");
      return;
    }

    const producto = {
      nombre: document.getElementById("nombre").value,
      descripcion: document.getElementById("descripcion").value,
      precio: parseFloat(document.getElementById("precio").value),
      cantidad: parseInt(document.getElementById("cantidad").value),
      categoria: document.getElementById("categoria").value,
      fecha: productoEnEdicion
        ? productoEnEdicion.fecha
        : new Date().toISOString().split("T")[0],
    };

    try {
      let response;
      if (productoEnEdicion) {
        // Edición
        response = await fetch(`${API_URL}/${productoEnEdicion.id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(producto),
        });
      } else {
        // Creación
        response = await fetch(API_URL, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(producto),
        });
      }

      if (!response.ok) {
        const errorData = await response.json();
        mostrarError(errorData.message || "Error al guardar el producto.");
        return;
      }

      mostrarExito(
        `Producto ${productoEnEdicion ? "editado" : "agregado"} con éxito.`
      );
      e.target.reset();
      productoEnEdicion = null;
      cargarProductos();
    } catch (error) {
      console.error("Error al conectar con la API:", error);
      mostrarError("No se pudo conectar con el servidor. Intenta más tarde.");
    }
  });

// Mostrar formulario para editar un producto
async function mostrarFormularioEditar(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) {
      throw new Error("No se pudo cargar el producto.");
    }

    const producto = await response.json();

    productoEnEdicion = producto;

    document.getElementById("nombre").value = producto.nombre;
    document.getElementById("descripcion").value = producto.descripcion;
    document.getElementById("precio").value = producto.precio;
    document.getElementById("cantidad").value = producto.cantidad;
    document.getElementById("categoria").value = producto.categoria;

    document.getElementById("productoForm").scrollIntoView(); // Llevar el formulario al usuario
  } catch (error) {
    console.error("Error al cargar el producto:", error);
    mostrarError("No se pudo cargar el producto.");
  }
}

// Eliminar producto
async function eliminarProducto(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      const errorData = await response.json();
      mostrarError(errorData.message || "Error al eliminar el producto.");
      return;
    }

    mostrarExito("Producto eliminado con éxito.");
    cargarProductos();
  } catch (error) {
    console.error("Error al conectar con la API:", error);
    mostrarError("No se pudo conectar con el servidor. Intenta más tarde.");
  }
}

// Validar texto
function validarTexto(texto) {
  const regex = /^[a-zA-Z\sáéíóúÁÉÍÓÚñÑ]+$/;
  return regex.test(texto);
}

// Mostrar error
function mostrarError(mensaje) {
  const errorDiv = document.getElementById("error-message");
  errorDiv.textContent = mensaje;
  errorDiv.style.display = "block";
  errorDiv.style.color = "red";
}

// Mostrar éxito
function mostrarExito(mensaje) {
  const successDiv = document.getElementById("success-message");
  successDiv.textContent = mensaje;
  successDiv.style.display = "block";
  successDiv.style.color = "green";
  setTimeout(() => {
    successDiv.style.display = "none";
  }, 3000);
}

cargarProductos();
