function Salir() {
    // configura peticion
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
        }
    }
    // enviar logout
    fetch('/api/auth/signout', options);
  }

  window.addEventListener('load', function() {
    Salir();
  });