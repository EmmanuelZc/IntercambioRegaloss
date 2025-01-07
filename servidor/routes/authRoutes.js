import express from 'express';
import authControllers from '../Controllers/authControllers.js'; 

const router = express.Router();

router.post('/registro', authControllers.registerUser);
router.post('/login', authControllers.loginUser);
router.post('/intercambio',authControllers.authenticate, authControllers.createIntercambio); 
export default router;
