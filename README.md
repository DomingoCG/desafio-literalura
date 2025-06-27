# 📚 LITERALURA

Es una aplicación Java basada en consola que permite explorar libros y autores a través de la API de [Gutendex](https://gutendex.com/). Puedes buscar libros, ver sus autores, filtrar por idioma, y guardarlos en una base de datos.

## 🚀 Características

- ✅ Consulta de libros desde la API de Gutendex
- ✅ Registro de autores y libros en base de datos
- ✅ Filtros por idioma, autor y título
- ✅ Integración con Spring Data JPA
- ✅ Almacenamiento en base de datos (por ejemplo: PostgreSQL o H2)

---

## 🛠️ Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- API Rest Gutendex
- Maven

---

## ⚙️ Cómo ejecutar el proyecto

1. **Clona el repositorio:**
2. Importa el proyecto en tu IDE favorito

3. Configura la conexión a la base de datos:  
Edita el archivo application.properties en src/main/resources:  
**Properties**  
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura  
spring.datasource.username=tu_usuario  
spring.datasource.password=tu_contraseña  
spring.jpa.hibernate.ddl-auto=update

4. Ejecuta la clase LiteraluraApplication

---

## 🗃️ Estructura del proyecto
literalura/  
├── model/             --> Entidades JPA: Libro, Autor  
├── repository/        --> Interfaces Spring Data  
├── service/           --> Lógica de negocio y API  
├── dto/               --> Records para la API  
├── resources/         --> Configuraciones (application.properties)  
└── Principal.java     --> Clase con el menú principal
