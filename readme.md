# 🔐 auth-service

Servicio de autenticación desarrollado con **Spring Boot**, encargado de registrar usuarios, iniciar sesión mediante **JWT**, obtener el usuario autenticado y administrar cuentas a través de endpoints para creación, actualización, activación/desactivación y eliminación permanente de usuarios.

---

## 📁 Estructura general del proyecto

```
karquitectura.auth_service
│
├── controller
│   ├── AuthController        # Registro, login, perfil del usuario autenticado
│   └── UserAdminController   # Administración de usuarios
│
├── dto
│   ├── RegisterRequest       # Datos para registrar usuario
│   ├── LoginRequest          # Datos para inicio de sesión
│   └── AuthResponse          # Token JWT
│
├── entity
│   └── User                  # Modelo de usuario
│
├── repository
│   └── UserRepository        # Acceso a BD
│
├── security
│   ├── SecurityConfig        # Configuración de rutas y seguridad
│   ├── JwtAuthenticationFilter
│   ├── JwtService            # Generación/validación de JWT
│   └── CustomUserDetailsService
│
└── service
    └── UserService           # Lógica de negocio
```

---

## 🧩 Explicación breve de cada componente

### **controller/**
- **AuthController**  
  Maneja el registro, login y el endpoint `/me` para obtener el usuario autenticado.

- **UserAdminController**  
  Permite al administrador listar, crear, actualizar, activar/desactivar y eliminar usuarios.

### **dto/**
Objetos usados para comunicar datos entre cliente y servidor:
- `RegisterRequest`
- `LoginRequest`
- `AuthResponse`

### **entity/**
- `User`: entidad principal del sistema (id, name, email, password, role, active).

### **repository/**
- `UserRepository`: incluye `findByEmail()` y operaciones CRUD.

### **security/**
- `JwtService`: genera y valida tokens JWT.
- `JwtAuthenticationFilter`: intercepta peticiones y autentica mediante JWT.
- `CustomUserDetailsService`: carga usuarios para Spring Security.
- `SecurityConfig`: define rutas públicas y protegidas.

### **service/**
- `UserService`: contiene la lógica central del registro, login y validaciones.

---

# 🔌 Endpoints del sistema

## 🔐 Autenticación (`/auth`)

### **POST /auth/register**
Registra un nuevo usuario.

**Body ejemplo:**
```json
{
  "name": "Juan Perez",
  "email": "juan@example.com",
  "password": "123456"
}
```

---

### **POST /auth/login**
Autentica al usuario y devuelve un token JWT.

**Body:**
```json
{
  "email": "juan@example.com",
  "password": "123456"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1..."
}
```

---

### **GET /auth/me**
Retorna el usuario autenticado.

Requiere header:
```
Authorization: Bearer <token>
```

---

## 🧑‍💼 Administración de usuarios (`/admin/users`)

### **GET /admin/users**
Lista todos los usuarios del sistema.

---

### **POST /admin/users**
Crea un usuario manualmente desde el panel de administración.

---

### **PUT /admin/users/{id}**
Actualiza los datos del usuario (nombre, email, contraseña, rol, estado).

---

### **PATCH /admin/users/{id}/toggle**
Activa o desactiva la cuenta del usuario.

- `active = true` → usuario habilitado
- `active = false` → usuario deshabilitado (no puede hacer login)

---

### **DELETE /admin/users/{id}**
Elimina el usuario de forma **permanente** de la base de datos.

---

## ✔ Resumen general

- `/auth/*` maneja registro, login y obtención del usuario autenticado.
- `/admin/users/*` permite la administración completa de cuentas.
- Seguridad basada en **JWT**, con filtro personalizado.
- Activación/desactivación mediante campo `active`.
- Eliminación física del usuario disponible vía DELETE.

---
