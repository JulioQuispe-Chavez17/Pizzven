function traer() {
                var dni = document.getElementById("dni").value;
                if (dni.length === 8) {
                    const token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";

                    var url = "https://dniruc.apisperu.com/api/v1/dni/" + dni + "?token=" + token;
                    fetch(url)
                            .then(Response => Response.json())
                            .then(data => {
                                document.getElementById("nombre").value = data.nombres;
                                document.getElementById("apellido_paterno").value = data.apellidoPaterno;
                                document.getElementById("apellido_materno").value = data.apellidoMaterno;

                            });

                }

            }



