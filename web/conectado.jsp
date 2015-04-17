<%@page import="com.tcc.xbeemonitor.XbeeDB"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>e-Parking :: Home</title>
    <style type="text/css">
        <!--
        body {
            background-image: url(images/bg.png);

        }
        -->
    </style>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/demo.css" />   
</head>

<body>

    <div class="content">
        <header>

            <div id="logo"><a href="index.jsp"><img src="images/logo.png" alt="Logo here" width="200px" height="80px" /></a> </div>
            <div id="dev" style="float: right;"> Desenvolvido por: Matheus Goes </div> 

        </header>


        <section>
            <div id="navigation" >
                <a href="#">Inicio </a>
                |<a href="sobre.jsp"> Sobre </a>
                |<a href="contato.jsp"> Contato</a>

            </div>
            <div class="transbox">
                <h2>Você está conectado ao sistema!</h2><h3>Escolha a ação:</h3>
                <form name="desconectar" action="controlServlet" method="POST">
                    <input type="hidden" name="acao" value="desconectar"/>
                    <input type="submit" value="Desconectar" style="border-radius: 5px 0px 0px 0px;"/>
                </form>               
                <form name="Ver dados" action="controlServlet" method="POST">
                    <input type="hidden" name="acao" value="verDados"/>
                    <input type="submit" value="Ver dados" style="border-radius: 0px 0px 5px 0px;"/>
                </form>
            </div>
        </section>
    </div>

</body>
</html>
