import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';

import 'assets/stylesheets/authentication.css';

function Login() {
  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome Back!" />
          <form className="px-4 px-sm-5 py-4">
            <FormItem label="Username" type="text" />
            <FormItem label="Password" type="password" />
            <button type="submit" className="miz-button w-100 mt-4 mb-3">Login</button>
            <p className="bottom-text text-center">Don't have an account? <a href="/signup" className="miz-text-red text-decoration-none">Sign up here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Login;
