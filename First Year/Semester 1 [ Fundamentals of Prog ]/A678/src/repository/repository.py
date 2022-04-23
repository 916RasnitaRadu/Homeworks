import pickle
from structure.structure import *


class Repository:
    def __init__(self):
        self._data = Struct()

    def get_all(self):
        return self._data.get_all()

    def save(self, entity):
        self._data[entity.id] = entity

    def remove(self, key):
        del self._data[key]

    def find_by_id(self, entity_id):
        if entity_id in self._data:
            return self._data[entity_id]
        return None

    def __getitem__(self, item):
        return self._data[item]

    def __setitem__(self, key, value):
        self._data[key] = value

    def __len__(self):
        return len(self._data)


class TextFileRepository(Repository):
    def __init__(self, file_path, entity):
        super().__init__()

        self._file_path = file_path
        self._entity = entity
        self._load_file()

    def _load_file(self):
        f = open(self._file_path, "rt")
        for line in f.readlines():
            if len(line) > 1:
                obj = self._entity.line_to_entity(line)
                self.save(obj)

        f.close()

    def _save_file(self):
        f = open(self._file_path, "wt")
        for entity in self._data.values():
            line = self._entity.entity_to_line(entity)
            f.write(line + "\n")

        f.close()

    def save(self, entity):
        super(TextFileRepository, self).save(entity)
        self._save_file()

    def remove(self, key):
        super(TextFileRepository, self).remove(key)
        self._save_file()

    def find_by_id(self, entity_id):
        return super(TextFileRepository, self).find_by_id(entity_id)
    
    def __getitem__(self, item):
        return super(TextFileRepository, self).__getitem__(item)
    
    def __setitem__(self, key, value):
        super(TextFileRepository, self).__setitem__(key, value)
        self._save_file()
        
    def get_all(self):
        return super(TextFileRepository, self).get_all()
    
    def __len__(self):
        return super(TextFileRepository, self).__len__()


class BinFileRepository(Repository):
    def __init__(self, file_path):
        super().__init__()

        self._file_path = file_path
        self._load_file()

    def _load_file(self):
        f = open(self._file_path, "rb")
        try:
            self._data = pickle.load(f)
        except EOFError:
            self._data = {}
        f.close()

    def _save_file(self):
        f = open(self._file_path, "wb")
        pickle.dump(self._data, f)
        f.close()

    def save(self, entity):
        super(BinFileRepository, self).save(entity)
        self._save_file()

    def remove(self, key):
        super(BinFileRepository, self).remove(key)
        self._save_file()

    def find_by_id(self, entity_id):
        return super(BinFileRepository, self).find_by_id(entity_id)

    def __getitem__(self, item):
        return super(BinFileRepository, self).__getitem__(item)

    def __setitem__(self, key, value):
        super(BinFileRepository, self).__setitem__(key, value)
        self._save_file()

    def get_all(self):
        return super(BinFileRepository, self).get_all()

    def __len__(self):
        return super(BinFileRepository, self).__len__()
