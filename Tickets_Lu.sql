Create table Usuario (
UUID_Usuario varchar2(150) not null,
nombreUsuario varchar2(25) not null,
contrasenaUsuario varchar2(20) not null
);

Create table Ticket (
UUID_Ticket varchar2(150) not null,
num_Ticket int not null,
Titulo varchar2(75) not null,
Descripcion varchar2(200) not null,
Autor varchar2(50) not null,
Email_autor varchar2(150) not null,
Fecha_ticket date not null,
Estado_ticket varchar2(150) not null,
Fecha_fin_ticket date not null
);