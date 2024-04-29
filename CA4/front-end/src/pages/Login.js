import Logo from '../assets/images/logo.png';
import FormItem from '../components/FormItem';

import '../assets/stylesheets/authentication.css';

function Login() {
  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <div className="d-flex justify-content-between align-items-center pt-4">
            <h1 className="miz-text-red fs-2 m-0">Welcome Back!</h1>
            <img src={Logo} alt="Mizdooni" height="96" width="120" />
          </div>
          <hr />
          <form className="px-4 px-sm-5 py-4">
            <FormItem label="username" type="text" />
            <FormItem label="password" type="password" />
            <button type="submit" className="miz-button w-100 mt-3 mb-3">Login</button>
            <p className="bottom-text text-center">Don't have an account? <a href="./signup.html" className="miz-text-red text-decoration-none">Sign up here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Login;
