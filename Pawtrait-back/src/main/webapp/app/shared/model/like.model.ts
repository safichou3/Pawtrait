import { IUser } from '@/shared/model/user.model';
import { IPhoto } from '@/shared/model/photo.model';

export interface ILike {
  id?: string;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
  user?: IUser | null;
  photo?: IPhoto | null;
}

export class Like implements ILike {
  constructor(
    public id?: string,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null,
    public user?: IUser | null,
    public photo?: IPhoto | null
  ) {}
}
