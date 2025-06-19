import React, { useEffect, useState } from "react";

function App() {
  const [users, setUsers] = useState([]);
  const [originalUsers, setOriginalUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [sortField, setSortField] = useState("");
  const [sortOrder, setSortOrder] = useState("asc");
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    fetch("http://localhost:8787/api/users")
      .then((res) => res.json())
      .then((data) => {
        setUsers(data);
        setOriginalUsers(data);
      })
      .catch((err) => console.error("Error fetching users:", err));
  }, []);

  const sortBy = (field) => {
    const order = sortField === field && sortOrder === "asc" ? "desc" : "asc";
    const sorted = [...users].sort((a, b) => {
      let aVal = a[field]?.toString().toLowerCase();
      let bVal = b[field]?.toString().toLowerCase();
      if (aVal < bVal) return order === "asc" ? -1 : 1;
      if (aVal > bVal) return order === "asc" ? 1 : -1;
      return 0;
    });

    setUsers(sorted);
    setSortField(field);
    setSortOrder(order);
  };

  const handleSearch = (e) => {
    const value = e.target.value.toLowerCase();
    setSearchQuery(value);
    const filtered = originalUsers.filter(
      (user) =>
        user.name.toLowerCase().includes(value) ||
        user.email.toLowerCase().includes(value)
    );
    setUsers(filtered);
  };

  const openUserModal = (id) => {
    fetch(`http://localhost:8787/api/users/${id}`)
      .then((res) => res.json())
      .then((data) => {
        setSelectedUser(data);
        const modal = new window.bootstrap.Modal(
          document.getElementById("userModal")
        );
        modal.show();
      })
      .catch((err) => console.error("Error fetching user detail:", err));
  };

  return (
    <div className="container my-4">
      <div className="text-center mb-4">
        <h2 className="fw-bold text-primary">User Management Panel</h2>
      </div>

      <div className="row mb-3">
        <div className="col-md-6 mx-auto">
          <input
            type="text"
            className="form-control"
            placeholder="🔍 Search by name or email..."
            value={searchQuery}
            onChange={handleSearch}
          />
        </div>
      </div>

      <div className="table-responsive shadow-sm">
        <table className="table table-hover align-middle mb-0">
          <thead className="table-primary sticky-top">
            <tr>
              <th
                style={{ cursor: "pointer" }}
                onClick={() => sortBy("id")}
                title="Sort by ID"
              >
                ID 🔁
              </th>
              <th
                style={{ cursor: "pointer" }}
                onClick={() => sortBy("name")}
                title="Sort by Name"
              >
                Name 🔁
              </th>
              <th
                style={{ cursor: "pointer" }}
                onClick={() => sortBy("email")}
                title="Sort by Email"
              >
                Email 🔁
              </th>
            </tr>
          </thead>
          <tbody>
            {users.length > 0 ? (
              users.map((user) => (
                <tr key={user.id}>
                  <td
                    style={{ color: "#0d6efd", cursor: "pointer" }}
                    onClick={() => openUserModal(user.id)}
                  >
                    {user.id}
                  </td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="3" className="text-center p-4">
                  No users found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* User Details Modal */}
      <div
        className="modal fade"
        id="userModal"
        tabIndex="-1"
        aria-labelledby="userModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content border-primary">
            <div className="modal-header bg-primary text-white">
              <h5 className="modal-title" id="userModalLabel">
                👤 User Details
              </h5>
              <button
                type="button"
                className="btn-close btn-close-white"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              {selectedUser ? (
                <div className="px-2">
                  <p>
                    <strong>ID:</strong> {selectedUser.id}
                  </p>
                  <p>
                    <strong>Name:</strong> {selectedUser.name}
                  </p>
                  <p>
                    <strong>Email:</strong> {selectedUser.email}
                  </p>
                  <p>
                    <strong>Phone:</strong> {selectedUser.phone}
                  </p>
                  <p>
                    <strong>Username:</strong> {selectedUser.username}
                  </p>
                </div>
              ) : (
                <p>Loading...</p>
              )}
            </div>
            <div className="modal-footer">
              <button
                className="btn btn-outline-warning"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
