function Pagination({ totalPages, currentPage, onPageChange }) {
  const renderPageNumbers = () => {
    const pageNumbers = [];
    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.push(
        <li key={i} className={i === currentPage ? 'active' : ''}>
          <button onClick={() => onPageChange(i)}>{i}</button>
        </li>
      );
    }
    return pageNumbers;
  };

  return (
    <nav className="mt-5">
      <ul className="review-pagination d-flex justify-content-center align-items-center gap-2">
        {renderPageNumbers()}
      </ul>
    </nav>
  );
}

export default Pagination;
