import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
// import Navbar from "../commonComponents/Navbar";
// import AddStockModal from "./AddStockModal";

import {
  Modal,
  Form,
  Select,
  InputNumber,
  Spin,
  Input,
  Checkbox,
  Divider,
} from "antd";

import { Button, Table, message, Empty } from "antd";
import {
  PlusOutlined,
  DeleteOutlined,
  HistoryOutlined,
  InboxOutlined,
} from "@ant-design/icons";
import moment from "moment";
import { getJournals, getCategories, getDifficultyLevels, createJournal, deleteJournalEntry, updateJournalEntry } from "../apiCalls/JournalCalls";

export default function UserHomePage() {
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  const user = useSelector((state) => state.user.user);

  const [journalPostingPage, setJournalPostingPage] = useState(false);
  const [loading, setLoading] = useState(false);

  const [journalEntries, setJournalEntries] = useState([]);
  const [categories, setCategories] = useState([]);
  const [difficultyLevels, setDifficultyLevels] = useState([]);
  const [updateJournal, setUpdateJournal] = useState(null);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/signin");
    } else {
      // fetchUserCart();
      // fetchImportedHistory();
      fetchJournalEntries();
      fetchCategories();
      fetchDifficultyLevels();
    }
  }, [isAuthenticated, navigate]);

  // const fetchUserCart = async () => {
  //   setLoading(true);
  //   try {
  //     const data = await getUserCart();
  //     setUserCart(data || []);
  //   } catch (error) {
  //     const errorMsg = error?.message || "Failed to load products";
  //     console.error("Error fetching user cart:", error);
  //     if (error?.status === 401 || errorMsg.includes("401")) {
  //       message.error("Authentication failed. Please login again.");
  //     } else {
  //       message.error(errorMsg);
  //     }
  //   } finally {
  //     setLoading(false);
  //   }
  // };

  // const fetchImportedHistory = async () => {
  //   setHistoryLoading(true);
  //   try {
  //     const data = await getImportedHistory();
  //     setImportedHistory(data || []);
  //   } catch (error) {
  //     const errorMsg = error?.message || "Failed to load history";
  //     console.error("Error fetching imported history:", error);
  //     if (error?.status === 401 || errorMsg.includes("401")) {
  //       message.error("Authentication failed. Please login again.");
  //     } else {
  //       message.error(errorMsg);
  //     }
  //   } finally {
  //     setHistoryLoading(false);
  //   }
  // };

  // const handleAddStock = async (productId, quantity, userId) => {
  //   setLoading(true);
  //   try {
  //     // Add to user cart or update if exists


  //     // Add to imported history
  //     await addToImportedHistory(productId, quantity, user.id);

  //     message.success("Stock added successfully!");
  //     setShowAddStock(false);
  //     fetchUserCart();
  //     fetchImportedHistory();
  //   } catch (error) {
  //     const errorMsg = error?.message || "Failed to add stock";
  //     message.error(errorMsg);
  //   } finally {
  //     setLoading(false);
  //   }
  // };

  // const productColumns = [
  //   {
  //     title: "Product Name",
  //     dataIndex: ["product", "productName"],
  //     key: "productName",
  //     render: (text) => <strong>{text}</strong>,
  //   },
  //   {
  //     title: "Supplier",
  //     dataIndex: ["product", "supplier", "companyName"],
  //     key: "supplier",
  //     render: (text) => text || "N/A",
  //   },
  //   {
  //     title: "Stock Quantity",
  //     dataIndex: "quantity",
  //     key: "quantity",
  //     align: "center",
  //     render: (quantity) => (
  //       <span className="quantity-badge">{quantity}</span>
  //     ),
  //   },
  //   {
  //     title: "Action",
  //     key: "action",
  //     align: "center",
  //     render: () => (
  //       <Button
  //         type="primary"
  //         icon={<PlusOutlined />}
  //         size="small"
  //         onClick={() => setShowAddStock(true)}
  //       >
  //         Add
  //       </Button>
  //     ),
  //   },
  // ];

  // const historyColumns = [
  //   {
  //     title: "Product Name",
  //     dataIndex: ["product", "productName"],
  //     key: "productName",
  //     render: (text) => <strong>{text}</strong>,
  //   },
  //   {
  //     title: "Quantity Added",
  //     dataIndex: "quantity",
  //     key: "quantity",
  //     align: "center",
  //   },
  //   {
  //     title: "Date",
  //     dataIndex: "date",
  //     key: "date",
  //     render: (date) => moment(date).format("DD/MM/YYYY"),
  //   },
  //   {
  //     title: "Time",
  //     dataIndex: "time",
  //     key: "time",
  //   },
  // ];

  const fetchJournalEntries = async () => {
    setLoading(true);
    try {
      const data = await getJournals();
      setJournalEntries(data || []);
    } catch (error) {
      const errorMsg = error?.message || "Failed to load journal entries";
      console.error("Error fetching journal entries:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    setLoading(true);

    try {
      const data = await getCategories();
      setCategories(data || []);
    } catch (error) {
      const errorMsg = error?.message || "Failed to load categories";
      console.error("Error fetching categories:", error);
    } finally {
      setLoading(false);
    }
  }

  const postJournalEntry = async (journalData) => {
    setLoading(true);
    try {
      await createJournal(journalData);
      // Refresh the journal entries after posting a new one
      await fetchJournalEntries();
    } catch (error) {
      const errorMsg = error?.message || "Failed to post journal entry";
      console.error("Error posting journal entry:", error);
    } finally {
      setLoading(false);
    }
  };
  const handleDelete = async (journalEntryId) => {
    setLoading(true);
    try {
      await deleteJournalEntry(journalEntryId);
      // Refresh the journal entries after deleting one
      await fetchJournalEntries();
    } catch (error) {
      const errorMsg = error?.message || "Failed to delete journal entry";
      console.error("Error deleting journal entry:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchDifficultyLevels = async () => {
    setLoading(true);
    try {
      const data = await getDifficultyLevels();
      setDifficultyLevels(data || []);
    } catch (error) {
      const errorMsg = error?.message || "Failed to load difficulty levels";
      console.error("Error fetching difficulty levels:", error);
    } finally {
      setLoading(false);
    }
  };
  if (!isAuthenticated) {
    return null;
  }

  const handleSubmit = async (values) => {
    if (updateJournal == null) {
      await postJournalEntry(values);
    } else {
      await updateJournalEntry(updateJournal.journalEntryId, values);
      await fetchJournalEntries();
      setUpdateJournal(null);
    }
    form.resetFields();
    setJournalPostingPage(false);
  };



  return (
    <div className="user-home-page">
      {/* <Navbar /> */}
      <div className="home-content">
        <div className="welcome-section">
          <h1>Welcome, {user?.username}! 👋</h1>
          <p>Manage your inventory stock efficiently</p>
          <Button onClick={() => setJournalPostingPage(true)}>Post a Journal Entry</Button>
        </div>


        <Modal
          title="Add Journal"
          open={journalPostingPage}
          onCancel={() => {
            setJournalPostingPage(false);
            setUpdateJournal(null);
            form.resetFields();
          }}
          onOk={() => form.submit()}
          // confirmLoading={loading}
          width={600}
        >
          <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            autoComplete="on"
            initialValues={{ isImportant: false, isFavorite: false }}
          >

            <Form.Item
              name="title"
              label="title"
              rules={[
                { required: true, message: "Please enter title" },
                {
                  type: "string",
                  min: 2,
                  message: "Title must be at least 2 characters",
                },
              ]}
            >
              <Input
                placeholder="Enter title"
                style={{ width: "100%" }}
              />
            </Form.Item>
            <Form.Item
              name="content"
              label="content"
              rules={[
                { required: true, message: "Please enter content" },
                { type: "string", min: 2, message: "Content must be at least 2 characters" }
              ]}
            >
              <Input
                placeholder="Enter content"
                style={{ width: "100%" }}
              />
            </Form.Item>

            <Form.Item
              name="categoryId"
              label="Select category"
              rules={[
                { required: true, message: "Please select a category" },
              ]}
            >
              <Select
                placeholder="Choose a category"
                // onChange={handleProductChange}
                optionLabelProp="label"
              >
                {categories.map((category) => (
                  <Select.Option
                    key={category.categoryId}
                    value={category.categoryId}
                  >
                    {category.categoryName}
                  </Select.Option>
                ))}
              </Select>
            </Form.Item>

            <Form.Item
              name="difficultyLevelId"
              label="Select difficulty level"
              rules={[
                { required: true, message: "Please select a difficulty level" },
              ]}
            >
              <Select
                placeholder="Choose a difficulty level"
                // onChange={handleProductChange}
                optionLabelProp="label"
              >
                {difficultyLevels.map((level) => (
                  <Select.Option key={level.difficultyLevelId} value={level.difficultyLevelId}>
                    <div className="difficulty-level-option">
                      <strong>{level.difficultyLevelName}</strong>
                    </div>
                  </Select.Option>
                ))}
              </Select>
            </Form.Item>

            <Form.Item
              name="isImportant"
              label="Mark as Important"
              valuePropName="checked"
              defaultValue={false}
            >
              <Checkbox>Mark as Important</Checkbox>
            </Form.Item>
            <Form.Item
              name="isFavorite"
              label="Mark as Favorite "
              valuePropName="checked"
              defaultValue={false}
            >
              <Checkbox>Mark as Favorite</Checkbox>
            </Form.Item>
          </Form>

        </Modal>



        <Table
          dataSource={journalEntries}
          rowKey="journalEntryId"
          pagination={{ pageSize: 5 }}
          locale={{
            emptyText: (
              <Empty description="No journal entries found" />
            )
          }}
          loading={loading}
        >
          <Table.Column title="Title" dataIndex="title" key="title" />
          <Table.Column title="Content" dataIndex="content" key="content" />
          <Table.Column title="Category" dataIndex="categoryName" key="categoryName" />
          <Table.Column title="Difficulty Level" dataIndex="difficultyLevelName" key="difficultyLevelName" />
          <Table.Column title="Important" dataIndex="isImportant" key="isImportant" render={(isImportant) => (isImportant ? "Yes" : "No")} />
          <Table.Column title="Favorite" dataIndex="isFavourite" key="isFavourite" render={(isFavourite) => (isFavourite ? "Yes" : "No")} />
          <Table.Column title="Created At" dataIndex="createdAt" key="createdAt" render={(createdAt) => moment(createdAt).format("DD/MM/YYYY HH:mm:ss")} />
          <Table.Column title="Updated At" dataIndex="updatedAt" key="updatedAt" render={(updatedAt) => moment(updatedAt).format("DD/MM/YYYY HH:mm:ss")} />
          //delete button
          <Table.Column
            title="Action"
            key="action"
            render={(_, record) => (
              <Button
                type="primary"
                danger
                onClick={() => handleDelete(record.journalEntryId)}
              >
                Delete
              </Button>
            )}
          />
          {/* edit buttton */}
          <Table.Column
            title="Edit"
            key="edit"
            render={(_, record) => (
              <Button
                type="primary"
                onClick={() => {
                  form.setFieldsValue({
                    title: record.title,
                    content: record.content,
                    categoryId: record.categoryId,
                    difficultyLevelId: record.difficultyLevelId,
                    isImportant: record.isImportant,
                    isFavorite: record.isFavourite
                  });
                  setUpdateJournal(record); setJournalPostingPage(true);
                }}
              >
                Edit
              </Button>
            )}
          ></Table.Column>

        </Table>
      </div>
    </div>
  );
}
