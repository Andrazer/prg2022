
function actualiza_tabla(){
    fetch('/brigada/all', { method: "GET" }).then(response => 
        response.json().then(data => ({
            data: data,
            status: response.status
        })
    ).then(res => {
        updateTable(res.data)
    }));
}

function borra_brigada(identificador){
    fetch('/brigada/del/'+identificador, { method: "DELETE" })
    .then(async response => {
    if (!response.ok) {
    const error = (data && data.message) || response.status;
    return Promise.reject(error);
    }
    else {
    window.location.reload();
    }
    })
    .catch(err => {
    alert("No ha sido posible borrar la brigada solicitada");
    })
};

function show_brigada(id){
  window.location.pathname='/brigada/show/'+id;
}

function updateTable(data){
//generamos la tabla a través del DOM automáticamente
var table = document.createElement("table");
    table.setAttribute("id", "tbl_brigadas");
var body = document.getElementById("bdy_brigada");
    body.innerHTML = '';
    body.appendChild(table).classList.add("table", "table-hover"); 
//campos a mostrar
const mostrar = ["id","creada","descripcion"];

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
row.appendChild(document.createElement("th")).setAttribute("scope", "col");
row.lastChild.innerHTML = "ACCIONES";
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
      if (j>2){ cell.classList.add("d-none","d-sm-table-cell"); }
      cell.innerHTML = data[i][key];
    }
  }
  if (!data[i]['activa']){
    row.classList.add("table-danger");
  }
    
}

//y tenemos que meter los botones en cada fila
//recorremos el tbody y metemos botones a cada uno de sus hijos (que serian los tr)
NodeList.prototype.forEach = Array.prototype.forEach;
var children = tbody.childNodes;
children.forEach(function(item){
    item.appendChild(document.createElement("td")).setAttribute("scope", "row");
    botonera = document.createElement("div");
    if (!item.classList.contains("table-danger")){
      botonera.appendChild(document.createElement("button")).classList.add("btn","btn-sm","btn-outline-success","ver");
      //botonera.appendChild(document.createElement("button")).classList.add("btn","btn-sm","btn-outline-warning","editar");
      botonera.appendChild(document.createElement("button")).classList.add("btn","btn-sm","btn-outline-danger","borrar");
      botonera.firstChild.setAttribute("onClick","show_brigada("+item.firstChild.textContent+")");
      botonera.lastChild.setAttribute("onClick","borra_brigada("+item.firstChild.textContent+")");
      
    } else {
      botonera.appendChild(document.createElement("button"));
      botonera.lastChild.classList.add("btn","btn-sm","btn-outline-danger");
      botonera.lastChild.innerHTML="recuperar elemento";
      botonera.lastChild.setAttribute("onClick","borra_brigada("+item.firstChild.textContent+")");

    }
    item.lastChild.appendChild(botonera);
});


}

window.onload = function() {
        actualiza_tabla();
};    


    function nueva_brigada(){
        document.getElementById("modalLoginForm").ariaHidden =false;
    }
   
    function Registrame() {

      
        $descripcion = document.getElementById("descripcion").value;
        $grupo = document.getElementById("grupo").value;
        $letra = document.getElementById("letra").value;
        $fecha_inicio = document.getElementById("fecha_inicio").value;
        $fecha_fin = document.getElementById("fecha_fin").value;

      if (!descripcion.checkValidity()){
        descripcion.classList.add('is-invalid')
        return;
      } else {
        descripcion.classList.remove('is-invalid')
        descripcion.classList.add('is-valid')
      }   
      
      if (!fecha_inicio.checkValidity()){
        fecha_inicio.classList.add('is-invalid')
        return;
      } else {
        fecha_inicio.classList.remove('is-invalid')
        fecha_inicio.classList.add('is-valid')
      }   

      if (!fecha_fin.checkValidity()){
        fecha_fin.classList.add('is-invalid')
        return;
      } else {
        fecha_fin.classList.remove('is-invalid')
        fecha_fin.classList.add('is-valid')
      }         
// create a JSON object
const json = { 
 "descripcion": $descripcion,
 "grupo": $grupo,
 "letra": $letra,
 "inicio": $fecha_inicio,
 "fin": $fecha_fin,
};

// request options
const options = {
method: 'POST',
body: JSON.stringify(json),
headers: {
   'Content-Type': 'application/json'
}
}

// send post request
fetch('/brigada/add', options)
.then(async response => {
  const isJson = response.headers.get('content-type')?.includes('application/json');
  const data = isJson ? await response.json() : null;  
  if (!response.ok) {

            // get error message from body or default to response status
            const error = (data && data.message) || response.status;
            return Promise.reject(error);
        }
        else {
          window.location.reload();
        }
})
.catch(err => {
  console.error(err);
  alert("No ha sido posible agregar la brigada solicitada");
});

}