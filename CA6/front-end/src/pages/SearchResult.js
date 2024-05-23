import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import PageLayout from 'components/PageLayout';
import Cards from 'components/Cards';
import Pagination from 'components/Pagination';
import { useLogout } from 'utils/logout';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';

function SearchResult() {
  useEffect(() => { document.title = 'Search Result'; }, []);
  const logout = useLogout();

  const [restaurants, setRestaurants] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const topText = 'Results for #' + searchParams;

  useEffect(() => {
    const fetchEffect = async () => {
      const response = await fetch(`/api/restaurants?${searchParams}&page=${currentPage}`);
      if (response.ok) {
        const resp = await response.json();
        setRestaurants(resp.data.pageList);
        setTotalPages(resp.data.totalPages);
      } else if (response.status == 401) {
        logout();
      } else {
        toast.error('Error fetching restaurants');
      }
    }
    fetchEffect();
  }, [searchParams, currentPage]);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <PageLayout>
      <Cards topText={topText} restaurants={restaurants} />
      <Pagination totalPages={totalPages} currentPage={currentPage} onPageChange={handlePageChange} />
    </PageLayout>
  );
}

export default SearchResult;
