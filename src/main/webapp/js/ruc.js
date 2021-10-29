      function traerRuc() {
       
        var ruc = document.getElementById("ruc").value;
        if (ruc.length === 11) {
         
          var mensaje = "Espere ....";
          document.getElementById("ejemplo").innerHTML = mensaje;
          const token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";

          var url =
            "https://dniruc.apisperu.com/api/v1/ruc/" + ruc + "?token=" + token;
          fetch(url)
            .then((Response) => Response.json())
            .then((data) => {
              document.getElementById("razonSocial").value = data.razonSocial;
              document.getElementById("direccion").value = data.direccion;
              mensaje = "Servicio exitoso";
              document.getElementById("ejemplo").innerHTML = mensaje;
            })
            .catch((err) => {
                document.getElementById("razonSocial").value = "";
              document.getElementById("direccion").value = "";
              mensaje = "Oops! Servicio no disponible";
              document.getElementById("ejemplo").innerHTML = mensaje;
            });
        }
      }  