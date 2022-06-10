function leyendo(codigoqr){
    /* comprobar si ya lo tenemos / ultimo antes de enviar una nueva peticion*/
    node = document.getElementById("detalleDatos"); //es el tbody
    for (let i = node.childNodes.length - 1; i >= 0; i--) {
      if (node.childNodes[i].getAttribute("data-qr")==codigoqr){
        return;
      }
    }         
    peticionServidor(codigoqr);
}

function peticionServidor(codigoqr){
    const data = new FormData();
    data.append('codigoqr', codigoqr);
    data.append('modo', get_modo());
    fetch('/resultadoControl', { method: "POST", body: data })
        .then(response => response.json()
        .then(data => ({ data: data, status: response.status }))
        .then(res => { interfaceAct(res,codigoqr); })
        )
        .catch(error =>{
        alert("El servidor no puede procesar el qr");
        }); 
}

function InterfaceClean(){
    document.getElementById("dasalida").classList.add("d-none");
    document.getElementById("fotos").classList.add("d-none");
    document.getElementById("datos").classList.add("d-none");
    node = document.getElementById("detalleDatos");
    for (let i = node.childNodes.length - 1; i >= 0; i--) {
        node.removeChild(node.childNodes[i]);
    }    
}

var salidaFrancos = function sFrancos(){
    node = document.getElementById("detalleDatos");
    const data = new FormData();
    for (let i = node.childNodes.length - 1; i >= 0; i--) {
        data.append('codigos', node.childNodes[i].childNodes[0].innerHTML);
    }   
    fetch('/salidafrancos', { method: "POST", body: data })
        .then(response => response.json()
        .then(data => ({ data: data, status: response.status }))
        .then(res => { 
            alert("Salida total realizada:  "+res.data["total"]);
            InterfaceClean();
        })
        )
        .catch(error =>{
        alert("El servidor no puede procesar la salida");
        }); 
}

function interfaceAct(res,codigoqr){
    if ( (!res.data["abordo"]) && (get_modo()) ){
        alert("Usuario en tierra, cambie a modo auto");
        return;
}
node = document.getElementById("detalleDatos");
document.getElementById("ultFoto").src="/fotos/"+res.data["foto"];
    if (document.getElementById("fotos").classList.contains("d-none")){
        document.getElementById("fotos").classList.remove("d-none");
        document.getElementById("datos").classList.remove("d-none");
        if ( get_modo() ){
        document.getElementById("dasalida").classList.remove("d-none");
        document.getElementById("dasalida").addEventListener('click', salidaFrancos , false);
        } 
    }
    var row = document.createElement("tr");
    row.setAttribute("data-qr",codigoqr);

            var cell = document.createElement("th");
            row.appendChild(cell).setAttribute("scope", "row");
            
                cell.innerHTML = codigoqr;
                cell.classList.add("d-none");
            node.appendChild(row);
                for (var x=0;x<2;x++){
                cell = document.createElement("td");
                row.appendChild(cell).setAttribute("scope", "row");
                if (x==0){
                    cell.innerHTML = res.data["numero"];
                    if (!get_modo()) {
                    if (res.data["abordo"]) {
                        cell.innerHTML = cell.innerHTML+" -> A bordo";
                    } else {
                        cell.innerHTML = cell.innerHTML+" -> En tierra";
                    }
                    } else {
                    document.getElementById("dasalida").innerHTML="Dar salida ("+node.children.length+")";

                    //contamos por brigada con  res.data["brigada"] de clave
                    console.log(res.data["brigada"]);
                    }
                }
                if (x==1){cell.innerHTML = res.data["nombre"];}
                //node.appendChild(row);
                //
                node.insertBefore(row, node.children[0]);
                }
    if (res.data["abordo"]){
        row.classList.add("table-success");
    } else{
        row.classList.add("table-danger");
    }
}

function inicia_lector(){  
InterfaceClean();
    let selectedDeviceId;
    const codeReader = new ZXing.BrowserMultiFormatReader()
    codeReader.listVideoInputDevices()
    .then((videoInputDevices) => {
        const sourceSelect = document.getElementById('sourceSelect')
        if (!videoInputDevices[0]){ 
        alert("No hay dispositivo de entrada");
        document.getElementById("lectorQrs").innerHTML='<a href="/lector">Volver a intentarlo</a>';
        return;
        } else {
        document.getElementById("lectorQrs").classList.remove("d-none");
        }
        selectedDeviceId = videoInputDevices[0].deviceId
        
        if (videoInputDevices.length >= 1) {
        videoInputDevices.forEach((element) => {
            const sourceOption = document.createElement('option')
            sourceOption.text = element.label
            sourceOption.value = element.deviceId
            sourceSelect.appendChild(sourceOption)
        })

        sourceSelect.onchange = () => {
            selectedDeviceId = sourceSelect.value;
        };

        const sourceSelectPanel = document.getElementById('sourceSelectPanel')
        sourceSelectPanel.style.display = 'block'
        }

        document.getElementById('startButton').addEventListener('click', () => {
        codeReader.decodeFromVideoDevice(selectedDeviceId, 'video', (result, err) => {
            if (result) {
            leyendo(result);
            }
            if (err && !(err instanceof ZXing.NotFoundException)) {
            }
        })
        })

        document.getElementById('startButton').click();

        document.getElementById('resetButton').addEventListener('click', () => {
        codeReader.reset()
        })

    })
    .catch((err) => {
    })



}

function get_modo(){
if (document.getElementById("tipos").innerHTML=="Salida de Francos") return true; else return false;
}

var cambia = function cambiaModo(){
InterfaceClean();
btn = document.getElementById("tipos");
if (get_modo()) {
    btn.innerHTML="Auto";
    btn.classList.remove("btn-outline-secondary");
    btn.classList.add("btn-warning");
} else {
    btn.innerHTML="Salida de Francos";
    btn.classList.remove("btn-warning");
    btn.classList.add("btn-outline-secondary");    
}
}
window.onload = function() {
inicia_lector();
document.getElementById("tipos").addEventListener('click', cambia , false);
};  