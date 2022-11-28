import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Detail from './routes/Detail';
import Emergency from './routes/Emergency';
import Mainpage from './routes/Mainpage';
import Modify from './routes/Modify';
import Register from './routes/Register';
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Mainpage />} />
          <Route path="/Emergency" element={<Emergency />} />
          <Route path="/Register" element={<Register />} />
          <Route path="/Detail/:id" element={<Detail />} />
          <Route path="/Detail/Modify/:id" element={<Modify />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
