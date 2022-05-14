
window.addEventListener('load', function() {
    parametros = getUrlVars();
    if (typeof parametros["error"] !== 'undefined'){
        muestra_error();
    }
});

function muestra_error(){
    document.getElementById("errormessage").classList.remove("d-none");  
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function CallWebAPI() {
    // formato de las variables con json
    const json = { 
            username: document.querySelector("#username").value,
            password: document.querySelector("#password").value
    };
    // configura peticion url
    const options = {
        method: 'POST',
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        }
    }
    // envia peticion de url
    fetch('/api/auth/signin', options)
        //.then(res => res.json())
        //.then(res => console.log(res.message))
        //.catch(err => console.error(err))
        .then(function(response){
            if(response.status==200){
                window.location.pathname = "/";
            } else {
                throw new Error(response.status);             
            }
        })
        .catch(err => {
            muestra_error();
            }
        );
}