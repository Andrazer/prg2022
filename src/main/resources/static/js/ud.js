function habilita(){
  fetch('/habilitaUser/[[${id}]]', { method: "GET" })
 .then(async response => {
   if (!response.ok) {
   const error = (data && data.message) || response.status;
   return Promise.reject(error);
   }
   else {
   alert("Usuario habilitado");
   window.location.reload();
   }
 })
 .catch(err => {
   alert("No ha sido posible habilitar el usuario");
   })
}

const edita_usr= event => {
   const data = new FormData();
   data.append('nombre', document.getElementById("edit_nombre").value);
   data.append('dni', document.getElementById("edit_dni").value);
   data.append('ape', document.getElementById("edit_apellido").value);
   data.append('ape2', document.getElementById("edit_apellido2").value);
   data.append('numero', document.getElementById("edit_numero").value);
   data.append('rancho', document.getElementById("edit_rancho").value);


 
 fetch('/usuarios/update/[[${id}]]', { method: "POST", body: data })
 .then(async response => {
   if (!response.ok) {
   const error = (data && data.message) || response.status;
   return Promise.reject(error);
   }
   else {
   alert("Datos recibidos");
   window.location.reload();
   }
 })
 .catch(err => {
   alert("No ha sido posible actualizar los datos");
   })
 }
 
 document.querySelector('#edita_usuario').addEventListener('submit', event => {
  edita_usr(event);
  event.preventDefault();
 });  

function borra_usuario(){
fetch('/usuarios/del/[[${id}]]', { method: "DELETE" })
.then(async response => {
  if (!response.ok) {
  const error = (data && data.message) || response.status;
  return Promise.reject(error);
  }
  else {
    window.location.href = '/brigada/show/[[${usuario.BrigadaId}]]'; 
  }
})
.catch(err => {
  alert("No ha sido posible borrar el usuario, ¿tiene autorizaciones o registros?");
  })
};

  function edita_usuario(){
    document.getElementById('edita_usuario').classList.remove('d-none');
    document.getElementById('panel').classList.add('d-none');
  }
  
  const subirImagen= event => {
   const archivos = event.target.files;
   const data = new FormData();
   data.append('fileDatas', archivos[0]);
 
 fetch('/subeFoto/[[${id}]]', { method: "POST", body: data })
 .then(async response => {
   if (!response.ok) {
   const error = (data && data.message) || response.status;
   return Promise.reject(error);
   }
   else {
   alert("Foto subida correctamente");
   window.location.reload();
   }
 })
 .catch(err => {
   alert("No ha sido posible subir la foto");
   })
 }
 
 document.querySelector('#fileDatas').addEventListener('change', event => {
     subirImagen(event);
 });