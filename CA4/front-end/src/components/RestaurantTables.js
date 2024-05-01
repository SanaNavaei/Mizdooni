import Hashtag from 'assets/icons/hashtag.svg';
import Seat from 'assets/icons/seat.svg';

function RestaurantTables({ tables }) {
  const noTableText = (
    <div className="container d-flex justify-content-center align-items-center flex-grow-1">
      <p>No Tables have been added.</p>
    </div>
  );

  let classContainer = "container pt-5 pb-3 px-5 ";
  if (tables.length == 0) {
    classContainer += "d-none";
  }

  return (
    <section id="tables" className="col-lg-8 h-100 overflow-auto p-2 d-flex flex-column">
      <div className="d-flex justify-content-start mb-3">
        <button className="miz-text-red manage-button-link fs-6 fw-normal ps-2 m-0">+Add Table</button>
      </div>
      <div className={classContainer}>
        <div className="row row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-xl-5 row-cols-xxl-6 g-4 text-white">
          {tables.map((table, index) => (
            <div className="col" key={index}>
              <div className="table-card mx-auto rounded-3 d-flex flex-column justify-content-center gap-1">
                <div className="d-flex justify-content-evenly align-items-center">
                  <img src={Hashtag} alt="Number" width="19" height="19" />
                  <span>{table.number}</span>
                </div>
                <div className="d-flex justify-content-evenly align-items-center">
                  <img src={Seat} alt="Seat" width="22" height="18" />
                  <span>{table.seats}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      {tables.length === 0 && noTableText}
    </section>
  );
}

export default RestaurantTables;
