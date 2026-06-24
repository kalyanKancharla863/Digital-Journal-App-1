import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
// import LandingPage from './components/LandingPage'
import LoginPage from './components/LoginPage'
import SignupPage from './components/SignupPage'
import UserHomePage from './components/UserHomePage'

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path='/' element={<LandingPage />} /> */}gbyuiergfjo;
        <Route path='/signin' element={<LoginPage />} />
        <Route path='/signup' element={<SignupPage />} />
        <Route path='/home' element={<UserHomePage />} />
      </Routes>
    </Router>
  )  
}

export default App
