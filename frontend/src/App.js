import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Detail from './routes/Detail';
import Emergency from './routes/Emergency';
import Mainpage from './routes/Mainpage';
import Register from './routes/Register';
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Mainpage />} />
          <Route path="/Emergency" element={<Emergency />} />
          <Route path="/Register" element={<Register />} />
          <Route path="/Detail" element={<Detail />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
