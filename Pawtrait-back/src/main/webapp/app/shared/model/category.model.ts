export interface ICategory {
  id?: string;
  name?: string | null;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
}

export class Category implements ICategory {
  constructor(
    public id?: string,
    public name?: string | null,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null
  ) {}
}
