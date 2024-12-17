import express from 'express';
import registerUser  from '../Controllers/authControllers.js';

const router = express.Router();

// Ruta para registrar un nuevo usuario
router.post('/registro', registerUser);


export default router;
