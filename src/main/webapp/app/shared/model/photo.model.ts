import { IUser } from '@/shared/model/user.model';
import { ICategory } from '@/shared/model/category.model';

export interface IPhoto {
  id?: string;
  photoUrl?: string | null;
  description?: string | null;
  cuteness?: number | null;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
  user?: IUser | null;
  category?: ICategory | null;
}

export class Photo implements IPhoto {
  constructor(
    public id?: string,
    public photoUrl?: string | null,
    public description?: string | null,
    public cuteness?: number | null,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null,
    public user?: IUser | null,
    public category?: ICategory | null
  ) {}
}
