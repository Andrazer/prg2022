  
const add_user= event => {
    const data = new FormData();
    data.append('nombre', document.getElementById("edit_nombre").value);
    data.append('dni', document.getElementById("edit_dni").value);
    data.append('ape', document.getElementById("edit_apellido").value);
    data.append('ape2', document.getElementById("edit_apellido2").value);
    data.append('numero', document.getElementById("edit_numero").value);
    data.append('rancho', document.getElementById("edit_rancho").value);
 
 
  
  fetch('/usuarios/addfull/'+idb, { method: "POST", body: data })
  .then(async response => {
    if (!response.ok) {
    const error = (data && data.message) || response.status;
    return Promise.reject(error);
    }
    else {
    alert("Alta recibida");
    window.location.reload();
    }
  })
  .catch(err => {
    alert("No ha sido posible actualizar los datos");
    })
  }
  
  document.querySelector('#add_usr').addEventListener('submit', event => {
   add_user(event);
   event.preventDefault();
  }); 

   
  const subirFotos= event => {
    const archivos = event.target.files;
    const data = new FormData();
    data.append('fileDatas', archivos[0]);
  
  fetch('/subeFotos/'+idb, { method: "POST", body: data })
  .then(async response => {
    if (!response.ok) {
    const error = (data && data.message) || response.status;
    return Promise.reject(error);
    }
    else {
    alert("Fotos subidas correctamente");
    window.location.reload();
    }
  })
  .catch(err => {
    alert("No ha sido posible subir las fotos");
    })
  }
  
  document.querySelector('#fotoszip').addEventListener('change', event => {
   subirFotos(event);
  });

  
  function actualiza_interface(data){
    document.getElementById("nombreBrigada").innerHTML=data.descripcion;
    document.getElementById("descripcion").innerHTML=data.descripcion;
    document.getElementById("marcas").innerHTML="| Creada: "+data.creada+" | Última actualización: "+data.actualizada+" |";
    if (data.inicio!=null){ document.getElementById("inicio").innerHTML=data.inicio; }
    if (data.fin!=null){ document.getElementById("fin").innerHTML=data.fin; }
    if (data.grupo!=0 || data.letra!=null){
      document.getElementById("detalles").innerHTML='Se importarán en la brigada '+data.grupo+data.letra;
      document.getElementById("grupo").innerHTML=
        '<span id="grp">'+data.grupo+'</span>'+
        '<span id="lttr">'+data.letra+'</span>';
        document.getElementById("edit_grp").value=data.grupo;
        document.getElementById("edit_letra").value=data.letra;

    };
    if (data.rf!=null){ document.getElementById("francos").innerHTML=data.rf; }

    
  }

 fetch('/brigada/get/'+idb, { method: "GET" }).then(response => 
  response.json().then(data => ({
      data: data,
      status: response.status
  })
).then(res => {
actualiza_interface(res.data)
get_alumnos();
}));
  


function finalizar_imp(){
fetch('/usuarios/ProcesaExcel/'+idb+'/'+document.getElementById("ranchodef").value, { method: "POST" })
.then(async response => {
if (!response.ok) {
const error = (data && data.message) || response.status;
return Promise.reject(error);
}
else {
alert("Importación realizada");
window.location.reload();
}
})
.catch(err => {
alert("No ha sido posible importar los usuarios");
})
};



const subirImagen= event => {
const archivos = event.target.files;
const data = new FormData();

data.append('fileDatas', archivos[0]);

fetch('/usuarios/NuevoExcel', { method: "POST", body: data })
.then(response => response.json()
.then(data => ({
  data: data,
  status: response.status
  })
)
.then(res => {
  document.getElementById("agregando_usuarios").classList.add("d-none");
  document.getElementById("agregando_usuarios2").classList.remove("d-none");
  importacion(res.data);
}))
.catch(error =>{
  alert("El servidor no puede procesar el archivo, compruebe formato Excel");
}); 







}

document.querySelector('#fileDatas').addEventListener('change', event => {
  subirImagen(event);
});






/*
var payload = {
  a: 1,
  b: 2
};

var data = new FormData();
data.append( "json", JSON.stringify( payload ) );

fetch("/echo/json/",
{
  method: "POST",
  body: data
})
.then(function(res){ return res.json(); })
.then(function(data){ alert( JSON.stringify( data ) ) })
*/


function getOperacion() {
    let name = "operacion=";
    let ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }
  
    function show_nombres(){
    const nombres = document.querySelectorAll('span.nombres');
    if(document.getElementById("flexCheckDefault").checked){
      for (const nombre of nombres) {
        nombre.classList.remove('d-none');
        nombre.parentNode.classList.remove('float-start');
      }
    } else{
      for (const nombre of nombres) {
        nombre.classList.add('d-none');
        nombre.parentNode.classList.add('float-start');
      }      
    }
    
    
    }

      function EditBrigada(){
        document.getElementById("desc_master").innerHTML=
          '<input id="descripcion" type="text" class="form-control" value="'+
          document.getElementById("descripcion").innerHTML+'" placeholder="'+
          document.getElementById("descripcion").innerHTML+'"'+
          ' aria-label="descripcion" aria-describedby="desclbl" required>';
  
          document.getElementById("desc_grupoletra").innerHTML=
          '<div class="input-group"><input id="grupo" type="text" class="form-control" placeholder="'+
          document.getElementById("grp").innerHTML+'" value="'+document.getElementById("grp").innerHTML+'"'+
          ' aria-label="Número que identifica la brigada" aria-describedby="numerolbl"  maxlength="2">'+
          '<input id="letra" type="text" class="form-control" placeholder="'+
          document.getElementById("lttr").innerHTML+'" value="'+document.getElementById("lttr").innerHTML+'"'+
          ' aria-label="Letra que identifica la brigada (ALFA, BRAVO, etc.." aria-describedby="letralbl"  maxlength="3"></div>';    
  
          $fecha_inicio=document.getElementById("inicio").innerHTML.split('/');
          if ($fecha_inicio.length>2){
            $fecha_inicio=new Date($fecha_inicio[2],$fecha_inicio[1],$fecha_inicio[0]); 
            document.getElementById("desc_inicio").innerHTML=
            '<input id="fecha_inicio" type="date" class="form-control" aria-label="Fecha de inicio"'+
            ' aria-describedby="fechainicio" required>'; 
            fecha_inicio.valueAsDate = 
              new Date(Date.UTC($fecha_inicio.getFullYear(), $fecha_inicio.getMonth()-1, $fecha_inicio.getDate()));
          } else {
              document.getElementById("desc_inicio").innerHTML=
            '<input id="fecha_inicio" type="date" class="form-control" aria-label="Fecha de inicio"'+
            ' aria-describedby="fechainicio" required>';           
          }
          $fecha_fin=document.getElementById("fin").innerHTML.split('/');
          if ($fecha_fin.length>2){
              
            $fecha_fin=new Date($fecha_fin[2],$fecha_fin[1],$fecha_fin[0]); 
            document.getElementById("desc_fin").innerHTML=
            '<input id="fecha_fin" type="date" class="form-control" aria-label="Fecha de Fin"'+
            ' aria-describedby="fecha_fin" required>'; 
            fecha_fin.valueAsDate = 
              new Date(Date.UTC($fecha_fin.getFullYear(), $fecha_fin.getMonth()-1, $fecha_fin.getDate())); 
          } else {
            document.getElementById("desc_fin").innerHTML=
            '<input id="fecha_fin" type="date" class="form-control" aria-label="Fecha de Fin"'+
            ' aria-describedby="fecha_fin" required>'; 
          }
           
          document.getElementById("btn_mod_brigada").classList.add("d-none");
          document.getElementById("btn_closemod_brigada").classList.remove("d-none");
          document.getElementById("btn_upd_brigada").classList.remove("d-none");
          document.getElementById("btn_sube_fotos").classList.add("d-none");
              
          
          
          
                  
      }
      function CancelEditBrigada(){
        document.location.reload();    
      }
      function PostEditBrigada(){
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/brigada/update/"+idb);
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
  
        xhr.onreadystatechange = function () {
          if (xhr.readyState === 4) {
            if (xhr.status===200){
              location.reload();
            } else{
              alert("Revise los datos introducidos, cambios no admitidos.");
            }
          }};
  $descripcion = document.getElementById("descripcion").value;
  $grupo = document.getElementById("grupo").value;
  $letra = document.getElementById("letra").value;
  $fecha_inicio = document.getElementById("fecha_inicio").value;
  $fecha_fin = document.getElementById("fecha_fin").value;
  const data = { 
   "descripcion": $descripcion,
   "grupo": $grupo,
   "letra": $letra,
   "inicio": $fecha_inicio,
   "fin": $fecha_fin,
  };
  
  xhr.send(JSON.stringify(data));
  
      }
  
  



  function importacion(data){
//generamos la tabla a través del DOM automáticamente
var table = document.createElement("table");
    table.setAttribute("id", "tbl_brigadas");
    table.classList.add("tableSection");
var body = document.getElementById("bdy_importando");
    body.innerHTML = '';
    body.appendChild(table).classList.add("table", "table-hover"); 
//campos a mostrar
const mostrar = ["nombre","apellido1","apellido2", "dni"];

if (data.length) {
  var thead = document.createElement("thead"); 
  var row = document.createElement("tr");
  thead.appendChild(row);
  table.appendChild(thead).classList.add("table-light"); 
  Object.keys(data[0]).forEach(function(v,i=0) {
    if (mostrar.includes(v)){
      i++;
      var cell = document.createElement("th");
      row.appendChild(cell).setAttribute("scope", "col");
      //oculto en movil, resto de columnas, dejando solo 1 y 2
      if (i>2){ cell.classList.add("d-none","d-sm-table-cell"); }
      //fin ocultacion
      cell.innerHTML = v.toUpperCase();
    }
  });
}



//lo anterior genera la tabla sola a partir del JSON
//si quiero meter funcionalidades a mayores, tengo que incluirlas.
//agregando botones, necesito un th mas para los botones:
//row.appendChild(document.createElement("th")).setAttribute("scope", "col");
//row.lastChild.innerHTML = "ACCIONES";
// ahora ya tenemos una columna para acciones, generamos filas en auto:
var tbody = document.createElement("tbody"); 

for (var i = 0; i < data.length; i++) {
  
  var row = document.createElement("tr");
  tbody.appendChild(row);
  table.appendChild(tbody);
  j=0;
  for (key in data[i]) {
    if (mostrar.includes(key)){
      j++;
      var cell = document.createElement("td");
      row.appendChild(cell).setAttribute("scope", "row");
      cell.innerHTML = data[i][key];
    }
  }
   
}

}


