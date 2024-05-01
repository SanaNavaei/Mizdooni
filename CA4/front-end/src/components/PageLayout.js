import Header from './Header';
import Footer from './Footer';

function PageLayout({ beforeMain, footerMargin, children }) {
  return (
    <div id="page-layout" className="min-vh-100 d-flex flex-column">
      <Header />
      {beforeMain}
      <main className="flex-grow-1">
        {children}
      </main>
      <Footer margin={footerMargin} />
    </div>
  );
}

export default PageLayout;
