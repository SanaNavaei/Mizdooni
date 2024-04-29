import Logo from '../assets/images/logo.png';
import FormItem from '../components/FormItem';

import '../assets/stylesheets/authentication.css';

function Login() {
  return (
    <div class="min-vh-100 d-flex flex-column justify-content-center">
      <main class="container-fluid px-0 py-4">
        <div id="container" class="mx-auto px-4 px-sm-5 rounded-4">
          <div class="d-flex justify-content-between align-items-center pt-4">
            <h1 class="miz-text-red fs-2 m-0">Welcome Back!</h1>
            <img src={Logo} alt="Mizdooni" height="96" width="120" />
          </div>
          <hr />
          <form class="px-4 px-sm-5 py-4">
            <FormItem label="username" type="text" />
            <FormItem label="password" type="password" />
            <button type="submit" class="miz-button w-100 mt-3 mb-3">Login</button>
            <p class="bottom-text text-center">Don't have an account? <a href="./signup.html" class="miz-text-red text-decoration-none">Sign up here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Login;
