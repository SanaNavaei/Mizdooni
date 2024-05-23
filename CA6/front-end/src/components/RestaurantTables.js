import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';

import AddTableModal from './AddTableModal';
import Hashtag from 'assets/icons/hashtag.svg';
import Seat from 'assets/icons/seat.svg';
import { useLogout } from 'utils/logout';

function RestaurantTables({ restaurantId, setTableNumber }) {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);
  const logout = useLogout();

  const reloadTables = async () => {
    const response = await fetch(`/api/tables/${restaurantId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
    });
    if (response.ok) {
      const body = await response.json();
      setTables(body.data);
    } else if (response.status === 401) {
      logout();
    } else {
      toast.error('Failed to fetch restaurant tables');
    }
  }

  useEffect(() => {
    reloadTables();
  }, [restaurantId]);

  const noTableText = (
    <div className="container d-flex justify-content-center align-items-center flex-grow-1">
      <p>No Tables have been added.</p>
    </div>
  );

  let classContainer = "container pt-5 pb-3 px-5 ";
  if (tables.length === 0) {
    classContainer += "d-none";
  }

  const handleTable = (tableNumber) => {
    setTableNumber(tableNumber);
    setSelectedTable(tableNumber);
  };

  return (
    <section id="tables" className="col-lg-8 h-100 overflow-auto p-2 d-flex flex-column">
      <div className="d-flex justify-content-start mb-3">
        <button className="miz-text-red manage-button-link fs-6 fw-normal ps-2 m-0" data-bs-toggle="modal" data-bs-target="#modal-add-table" >+Add Table</button>
      </div>
      <div className={classContainer}>
        <div className="row row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-xl-5 row-cols-xxl-6 g-4 text-white">
          {tables.map((table, index) => (
            <div className="col" key={index}>
              <button
                className={"table-card border-0 mx-auto rounded-3 d-flex flex-column justify-content-center gap-1" + (selectedTable === table.tableNumber ? " table-card-selected" : "")}
                onClick={() => handleTable(table.tableNumber)}
              >
                <div className="d-flex justify-content-evenly align-items-center">
                  <img src={Hashtag} alt="Number" width="19" height="19" />
                  <span className="text-white">{table.tableNumber}</span>
                </div>
                <div className="d-flex justify-content-evenly align-items-center">
                  <img src={Seat} alt="Seat" width="22" height="18" />
                  <span className="text-white">{table.seatsNumber}</span>
                </div>
              </button>
            </div>
          ))}
        </div>
      </div>
      {tables.length === 0 && noTableText}
      <AddTableModal restaurantId={restaurantId} reloadTables={reloadTables} />
    </section >
  );
}

export default RestaurantTables;
