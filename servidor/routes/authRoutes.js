import express from 'express';
import authControllers from '../Controllers/authControllers.js'; 

const router = express.Router();

router.post('/registro', authControllers.registerUser);
router.post('/login', authControllers.loginUser);
router.post('/intercambio',authControllers.authenticate, authControllers.createIntercambio); 
router.post('/temas', authControllers.authenticate, authControllers.addTema); 
router.get('/intercambio', authControllers.authenticate, authControllers.getIntercambios)
router.get('/clave')
export default router;
