import Header from './Header';
import Footer from './Footer';

function PageLayout({ children }) {
  return (
    <div className="min-vh-100 d-flex flex-column">
      <Header />
      <main className="flex-grow-1">
        {children}
      </main>
      <Footer />
    </div>
  );
}

export default PageLayout;
