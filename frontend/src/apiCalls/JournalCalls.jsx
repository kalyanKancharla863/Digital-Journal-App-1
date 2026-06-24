import api from "./axios";

export const getCategories = async () => {
    return (await api.get("/api/categories")).data;
}

export const getJournals = async () => {
    return (await api.get("/api/journal-entries")).data;
}

export const deleteJournal = async (journalId) => {
    return (await api.delete(`/api/journal-entry/${journalId}`)).data;
}

export const updateJournalEntry = async (journalId, updatedData) => {
    return (await api.put(`/api/journal-entry/${journalId}`, updatedData)).data;
}

export const createJournal = async (journalData) => {
    return (await api.post("/api/journal-entry", journalData)).data;
}

export const getDifficultyLevels = async () => {
    return (await api.get("/api/difficultylevels")).data;
}
//    @DeleteMapping("/journal-entry/{id}")

export const deleteJournalEntry = async (id) => {
    return (await api.delete(`/api/journal-entry/${id}`)).data;
}
