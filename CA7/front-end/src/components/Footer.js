function Footer({ margin = 5 }) {
  return (
    <footer id="footer" className={`container-fluid py-2 mt-${margin}`}>
      <small className="d-block text-white text-center fw-bold m-0">Copyright &copy; 2024 Mizdooni - All rights reserved.</small>
    </footer>
  );
}

export default Footer;
