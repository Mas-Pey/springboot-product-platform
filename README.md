# Product Platform (Microservices - Spring Boot)

---

## Arsitektur & Daftar Service

| Urutan Start | Service | Port | Fungsi Utama |
| :---: | :--- | :---: | :--- |
| **1** | `eureka` | `8761` | Service registry / discovery server |
| **2** | `auth-service` | `8081` | Register, login (BCrypt), dan generate token JWT |
| **3** | `product-service` | `8082` | CRUD produk, filter nama, validasi rating & duplikat impor |
| **4** | `api-gateway` | `8000` | Single entry point, validasi JWT, routing `lb://` |
| **5** | `scheduler-service` | `8083` | Tarik data smartphones DummyJSON (startup & tiap 2 menit) -> kirim ke `product-service` |

---

## How to Run

### 1. Jalankan Database PostgreSQL (Docker)
Pastikan container PostgreSQL sudah berjalan:

```bash
docker compose up -d
```

### 2. Siapkan File `.env` di Root Folder
Buat file `.env` di root folder `bt-platform/` (file ini di-ignore oleh Git):

```env
DB_URL=jdbc:postgresql://localhost:5432/db_name
DB_USERNAME=db_user
DB_PASSWORD=db_pw
JWT_SECRET=secret_key
JWT_EXPIRATION=3600000
EUREKA_URI=http://localhost:8761/eureka
```

### 3. Jalankan App Service
1. **`eureka`** (`:8761`)
2. **`auth-service`** (`:8081`)
3. **`product-service`** (`:8082`)
4. **`api-gateway`** (`:8000`)
5. **`scheduler-service`** (`:8083`)

---

## Daftar Endpoint (Via API Gateway `:8000`)

| Method | Endpoint | Auth | Keterangan |
| :---: | :--- | :---: | :--- |
| `POST` | `/auth/register` | Publik | Daftar user baru (password di-hash BCrypt) |
| `POST` | `/auth/login` | Publik | Login user & dapatkan token JWT |
| `POST` | `/product` | Bearer JWT | Buat produk baru (`201 Created`) |
| `GET` | `/product` | Bearer JWT | Ambil semua produk (opsional filter `?huruf=a`) |
| `GET` | `/product/{id}` | Bearer JWT | Ambil detail produk per ID (`404` jika tidak ada) |
| `PUT` | `/product/{id}` | Bearer JWT | Update data produk per ID |
| `DELETE` | `/product/{id}` | Bearer JWT | Hapus produk per ID |
| `POST` | `/scheduler/import` | Bearer JWT | Trigger manual impor batch produk dari DummyJSON |
| `POST` | `/product/internal/import` | **Ditolak Gateway (`403`)** | Khusus internal (`scheduler-service` -> `product-service` via Feign) |

---

## Catatan Keputusan

1. Isi claim sub pada JWT memakai username (bukan id) dan verifikasi token dilakukan terpusat di API Gateway, sehingga masing-masing service di belakangnya tidak perlu validasi token atau query ulang ke database user, cukup membaca identitas dari header X-User-Id yang diteruskan Gateway dan juga pencatatan log menjadi informatif.

2. Cek Duplikat Produk Berdasarkan Nama (Ignore Case) ditambah aturan unique di kolom tabel agar mencegah duplikat data.

3. Pengaturan Path Langsung di Controller (Tanpa Context-Path) supaya config routing lebih simpel tanpa perlu rewrite path.

4. Endpoint /product/internal/import sengaja diblokir di SecurityConfig Gateway (denyAll) agar tidak bisa dihit dari luar meskipun memakai token JWT.

5. Satu File .env di Root Folder terkait data sensitif seperti password database dan secret JWT.


